package com.mitbook.support.aspect;

import com.mitbook.core.MitGlobalTransactionManager;
import com.mitbook.core.MitTruncationBuilder;
import com.mitbook.core.ChildTransaction;
import com.mitbook.support.anno.MitTransactional;
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
public class MitTransactionalAspect {
    
    @Autowired
    private MitGlobalTransactionManager mitGlobalTransactionManager;
    
    @Autowired
    private RedisTemplate redisTemplate;
    
    @Pointcut("@annotation(com.mitbook.support.anno.MitTransactional)")
    public void pointCut() {
    
    }
    
    @Around("pointCut()")
    public void invoke(ProceedingJoinPoint joinPoint) throws Exception {
        
        Method targetMethod = getTargetMethod(joinPoint);
        
        //获取目标方法的注解
        MitTransactional mitTransactional = targetMethod.getAnnotation(MitTransactional.class);
        
        //获取注解属性对象
        TransactionalType transactionalType = mitTransactional.transType();
        
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
            mitGlobalTransactionManager.saveToRedis(childTransaction);
            
            //调用目标方法,我们需要在目标新开一个线程去监控redis的值是否变化来决定,本地事务是提交还是回滚
            joinPoint.proceed();
            
            //目标方法没有抛出异常  修改中间状态为COMMIT状态
            childTransaction.setTransactionalStatusCode(TransactionalStatus.COMMIT.getCode());
            mitGlobalTransactionManager.saveToRedis(childTransaction);
            
        } catch (Throwable throwable) {
            log.error("保存子事务状态到redis中抛出异常:globalId:{},childId:{},异常:{}", childTransaction.getGlobalTransactionalId(),
                    childTransaction.getChildTransactionalId(), throwable.getStackTrace());
            //调用本地事务方法异常的话,修改当前子事务状态为ROLLBACK状态
            childTransaction.setTransactionalStatusCode(TransactionalStatus.RollBACK.getCode());
            mitGlobalTransactionManager.saveToRedis(childTransaction);
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
    private ChildTransaction builderChildTransaction(Integer transactionalTypeEnumCode,
            Integer transactionalEnumStatusCode) {
        MitTruncationBuilder mitTruncationBuilder = new MitTruncationBuilder();
        String childTransId = GlobalAndChildTransactionId.generatorChildTransactionalId();
        TransactionalHolder.setChild(childTransId);
        return mitTruncationBuilder.builderTransactionalTypeEnumCode(transactionalTypeEnumCode)
                .builderChildTransactionalId(childTransId).builderTransactionalEnumStatus(transactionalEnumStatusCode)
                .builderGlobalTransactionId(TransactionalHolder.get()).builder();
    }
}
