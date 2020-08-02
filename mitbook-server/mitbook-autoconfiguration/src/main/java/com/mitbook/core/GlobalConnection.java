/*
 * Copyright 1999-2020 Mitbook Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mitbook.core;

import com.mitbook.support.enumeration.TransactionalStatus;
import com.mitbook.support.holder.TransactionalProperties;
import com.mitbook.support.holder.TransactionalHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 数据库连接对象
 *
 * @author pengzhengfa
 */
@Slf4j
public class GlobalConnection extends GlobalAbstractConnection {
    
    /**
     * 自己默认的构造器
     *
     * @param connection 数据库连接
     */
    public GlobalConnection(Connection connection, GlobalTransactionManager globalTransactionManager,
            TransactionalProperties transactionalProperties) {
        super(connection, globalTransactionManager, transactionalProperties);
    }
    
    /**
     * 我们需要在这里动手脚
     *
     * @throws SQLException
     */
    @Override
    public void commit() throws SQLException {
        
        //从线程变量中获取全局事务id
        String globalTransactionId = TransactionalHolder.get();
        
        //没有加入到分布式事务中,使用本地的事务
        if (StringUtils.isEmpty(TransactionalHolder.getChild())) {
            log.info("do not join in distributed transaction, use local transaction");
            getConnection().commit();
            return;
        }
        //开启一个新的线程去监控redis内存值的变化
        ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(10);
        
        AtomicLong count = new AtomicLong(0);
        
        //定时线程池
        pool.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                //轮询的去监控redis的值的变化
                Integer globalTransStatus = getGlobalTransactionManager()
                        .calChildTransactionStatus(globalTransactionId);
                TransactionalStatus transactionalStatus = TransactionalStatus.getByCode(globalTransStatus);
                log.info("distributed transaction:{}monitor value of:{}", globalTransactionId, globalTransStatus);
                
                switch (transactionalStatus) {
                    case COMMIT:
                        log.info("commit distributed transaction:{}", globalTransactionId);
                        globalCommit(getConnection(), pool);
                    case RollBACK:
                        log.info("rollBack distributed transaction:{}", globalTransactionId);
                        globalRollBack(getConnection(), pool);
                    case WAITING:
                        if (count.addAndGet(getTransactionalProperties().getDelay()) > getTransactionalProperties()
                                .getWaitingTime()) {
                            globalRollBack(getConnection(), pool);
                        }
                }
            }
        }, getTransactionalProperties().getInitialDelay(), getTransactionalProperties().getDelay(), TimeUnit.SECONDS);
    }
    
    /**
     * 我们需要在这里动手脚
     *
     * @throws SQLException
     */
    @Override
    public void rollback(){
        try {
            //没有加入到分布式事务中,使用的是本地事务
            if (StringUtils.isEmpty(TransactionalHolder.getChild())) {
                log.info("do not join in distributed transaction, use local transaction");
                getConnection().rollback();
            } else {
                commit();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    /**
     * 我们需要在这里动手脚
     *
     * @throws SQLException
     */
    @Override
    public void close() throws SQLException {
    
    }
    
    /**
     * 全局事务提交
     *
     * @param connection
     * @param pool
     */
    private void globalCommit(Connection connection, ScheduledExecutorService pool) {
        try {
            connection.commit();
        } catch (SQLException e) {
            log.error("commit transaction exception:{}", e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("close database connection exception:{}", e);
            }
        }
        pool.shutdownNow();
    }
    
    /**
     * 分支事务回滚
     *
     * @param connection
     * @param pool
     */
    private void globalRollBack(Connection connection, ScheduledExecutorService pool) {
        try {
            //分布式事务不能提交
            connection.rollback();
        } catch (SQLException e) {
            log.info("rollBack local transaction exception:{}", e.getMessage());
        } finally {
            try {
                getConnection().close();
            } catch (SQLException e) {
                log.info("close connection exception:{}", e.getMessage());
            }
        }
        pool.shutdownNow();
    }
}