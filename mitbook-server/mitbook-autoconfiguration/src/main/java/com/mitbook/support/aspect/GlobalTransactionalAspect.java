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

import com.mitbook.core.GlobalTransactionManager;
import com.mitbook.core.GlobalTruncationBuilder;
import com.mitbook.core.ChildTransaction;
import com.mitbook.support.anno.GlobalTransactional;
import com.mitbook.support.enumeration.TransactionalType;
import com.mitbook.support.enumeration.TransactionalStatus;
import com.mitbook.support.holder.TransactionalHolder;
import com.mitbook.support.holder.GlobalAndChildTransactionId;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;

/**
 * 事务切面
 *
 * @author pengzhengfa
 */
@Aspect
@Order(0)
@Slf4j
public class GlobalTransactionalAspect {
    
    @Autowired
    private GlobalTransactionManager globalTransactionManager;
    
    @Autowired
    private RedisTemplate redisTemplate;
    
    @Pointcut("@annotation(com.mitbook.support.anno.GlobalTransactional)")
    public void pointCut() {
    
    }
    
    @Around("pointCut()")
    public void invoke(ProceedingJoinPoint joinPoint) {
        
        Method targetMethod = getTargetMethod(joinPoint);
        
        //获取目标方法的注解
        GlobalTransactional globalTransactional = targetMethod.getAnnotation(GlobalTransactional.class);
        
        //获取注解属性对象
        TransactionalType transactionalType = globalTransactional.transType();
        
        //判断是不是分布式事务开始节点
        if (transactionalType.getCode() == TransactionalType.BEGIN.getCode()) {
            
            //生成全局唯一id
            String gableTransactionId = GlobalAndChildTransactionId.generatorGlobalTransactionalId();
            
            //放入线程变量中
            TransactionalHolder.set(gableTransactionId);
        }
        
        //使用建造者模式来构建子事务对象(此时的事务对象的状态是中间状态 WAFTING状态)
        ChildTransaction childTransaction = builderChildTransaction(transactionalType.getCode(),
                TransactionalStatus.WAITING.getCode());
        
        try {
            
            //把子事务对象上报到分布式事务管理中心
            globalTransactionManager.saveToRedis(childTransaction);
            
            //调用目标方法,我们需要在目标新开一个线程去监控redis的值是否变化来决定,本地事务是提交还是回滚
            joinPoint.proceed();
            
            //目标方法没有抛出异常  修改中间状态为COMMIT状态
            childTransaction.setTransactionalStatusCode(TransactionalStatus.COMMIT.getCode());
            globalTransactionManager.saveToRedis(childTransaction);
            
        } catch (Throwable throwable) {
            log.error("save the child transaction state to redis and throw an exception:globalId:{},childId:{},exception:{}", childTransaction.getGlobalTransactionalId(),
                    childTransaction.getChildTransactionalId(), throwable.getStackTrace());
            //调用本地事务方法异常的话,修改当前子事务状态为ROLLBACK状态
            childTransaction.setTransactionalStatusCode(TransactionalStatus.RollBACK.getCode());
            globalTransactionManager.saveToRedis(childTransaction);
            throw new RuntimeException(throwable.getMessage());
        }
        
    }
    
    /**
     * 获取目标方法
     *
     * @param proceedingJoinPoint
     */
    private Method getTargetMethod(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        return signature.getMethod();
    }
    
    /**
     * 构造子事务对象
     */
    private ChildTransaction builderChildTransaction(Integer transactionalTypeCode, Integer transactionalStatusCode) {
        GlobalTruncationBuilder globalTruncationBuilder = new GlobalTruncationBuilder();
        String childTransId = GlobalAndChildTransactionId.generatorChildTransactionalId();
        TransactionalHolder.setChild(childTransId);
        return globalTruncationBuilder.builderTransactionalTypeCode(transactionalTypeCode)
                .builderChildTransactionalId(childTransId).builderTransactionalStatus(transactionalStatusCode)
                .builderGlobalTransactionId(TransactionalHolder.get()).builder();
    }
}
