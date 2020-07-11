package com.mitbook.core;

import com.mitbook.support.enumaration.TransactionalEnumStatus;
import com.mitbook.support.holder.MitDtProperties;
import com.mitbook.support.holder.MitTransactionalHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 我们分布式框架自己改写的数据库连接对象
 *
 * @author pengzhengfa
 */
@Slf4j
public class MitConnection extends MitAbstractConnection {

    /**
     * 自己默认的构造器
     *
     * @param connection 数据库连接
     */
    public MitConnection(Connection connection, MitGlobalTransactionManager mitGlobalTransactionManager, MitDtProperties mitDtProperties) {
        super(connection, mitGlobalTransactionManager, mitDtProperties);
    }

    /**
     * 我们需要在这里动手脚
     *
     * @throws SQLException
     */
    @Override
    public void commit() throws SQLException {

        //从线程变量中获取全局事务ID
        String globalTransactionId = MitTransactionalHolder.get();

        //没有加入到分布式事务中，使用本地的事务
        if (StringUtils.isEmpty(MitTransactionalHolder.getChild())) {
            log.info("没有加入到分布式事务中,使用本地事务");
            getConnection().commit();
            return;
        }

        //开启一个新的线程去监控redis内存值的变化
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(10);

        AtomicLong count = new AtomicLong(0);
        //定时线程池
        pool.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                //轮询的去监控redis的值的变化
                Integer globalTransStatus = getMitGlobalTransactionManager().calChildTransactionStatus(globalTransactionId);
                TransactionalEnumStatus transactionalEnumStatus = TransactionalEnumStatus.getbycode(globalTransStatus);
                log.info("分布式事务:{}的监控值:{}", globalTransactionId, globalTransStatus);

                switch (transactionalEnumStatus) {
                    case COMMIT:

                        log.info("提交分布式事务:{}", globalTransactionId);
                        globalCommit(getConnection(), pool);
                    case RollBACK:

                        log.info("回滚分布式事务:{}", globalTransactionId);
                        globalRollBack(getConnection(), pool);
                    case WAITING:
                        if (count.addAndGet(getMitDtProperties().getDelay()) > getMitDtProperties().getWatingTime()) {
                            globalRollBack(getConnection(), pool);
                        }
                }
            }
        }, getMitDtProperties().getInitialDelay(), getMitDtProperties().getDelay(), TimeUnit.SECONDS);
    }

    /**
     * 我们需要在这里动手脚
     *
     * @throws SQLException
     */
    @Override
    public void rollback() throws SQLException {
        //没有加入到分布式事务中,使用的是本地事务
        if (StringUtils.isEmpty(MitTransactionalHolder.getChild())) {
            log.info("没有加入到分布式事务中,使用本地事务");
            getConnection().rollback();
        } else {
            commit();
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

    private void globalCommit(Connection connection, ScheduledExecutorService pool) {
        try {
            connection.commit();
        } catch (SQLException e) {
            log.error("提交事务异常:{}", e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("关闭数据库连接异常:{}", e);
            }
        }
        pool.shutdownNow();
    }

    private void globalRollBack(Connection connection, ScheduledExecutorService pool) {
        //分布式事务不能提交
        try {
            connection.rollback();

        } catch (SQLException e) {
            log.info("回滚本地事务异常:{}", e.getMessage());
        } finally {
            try {
                getConnection().close();
            } catch (SQLException e) {
                log.info("关闭连接异常:{}", e.getMessage());
            }
        }
        pool.shutdownNow();
    }

}
