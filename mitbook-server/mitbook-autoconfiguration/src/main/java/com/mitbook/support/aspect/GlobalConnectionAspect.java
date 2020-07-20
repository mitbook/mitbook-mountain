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
