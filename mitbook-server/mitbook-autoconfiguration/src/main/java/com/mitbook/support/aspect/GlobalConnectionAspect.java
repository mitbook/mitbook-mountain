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
package com.mitbook.support.aspect;

import com.mitbook.core.GlobalConnection;
import com.mitbook.core.GlobalTransactionManager;
import com.mitbook.support.holder.TransactionalProperties;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import java.sql.Connection;

/**
 * 用于切入数据源 getConnection()方法的
 *
 * @author pengzhengfa
 */
@Aspect
@Order(1)
public class GlobalConnectionAspect {
    
    @Autowired
    private GlobalTransactionManager globalTransactionManager;
    
    @Autowired
    private TransactionalProperties transactionalProperties;
    
    /**
     * 环绕通知
     *
     * @param proceedingJoinPoint
     */
    @Around("execution(* javax.sql.DataSource.getConnection(..))")
    public Connection cutConnectionMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //返回数据库原生的Connection
        Connection connection = (Connection) proceedingJoinPoint.proceed();
        //保证成我们自己的数据库连接,然后获取控制权
        GlobalConnection angleConnection = new GlobalConnection(connection, globalTransactionManager,
                transactionalProperties);
        return angleConnection;
    }
}
