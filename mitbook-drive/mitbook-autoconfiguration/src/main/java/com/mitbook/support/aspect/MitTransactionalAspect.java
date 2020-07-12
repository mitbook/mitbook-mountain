package com.mitbook.support.aspect;

import com.mitbook.core.MitGlobalTransactionManager;
import com.mitbook.core.MitTruncationBuilder;
import com.mitbook.core.ChildTransaction;
import com.mitbook.support.anno.MitTransactional;
import com.mitbook.support.enumaration.TransactionalTypeEnum;
import com.mitbook.support.enumaration.TransactionalEnumStatus;
import com.mitbook.support.holder.MitTransactionalHolder;
import com.mitbook.support.utils.MitDtUtil;
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
        TransactionalTypeEnum transactionalTypeEnum = mitTransactional.transType();
        
        //判断是不是分布式事务开始节点
        if (transactionalTypeEnum.getCode() == TransactionalTypeEnum.BEGIN.getCode()) {
            
            //生成全局唯一ID
            String gableTransactionId = MitDtUtil.generatorGlobalTransactionalId();
            
            //放入线程变量中
            MitTransactionalHolder.set(gableTransactionId);
        }
        
        //使用建造者模式来构建子事务对象(此时的事务对象的状态是中间状态 WATING状态)
        ChildTransaction childTransaction = builderChildTransactionObj(transactionalTypeEnum.getCode(),
                TransactionalEnumStatus.WAITING.getCode());
        
        try {
            
            //把子事务对象上报到分布式事务管理中心
            mitGlobalTransactionManager.saveToRedis(childTransaction);
            
            //调用目标方法,我们需要在目标新开一个线程去监控redis的值是否变化来决定,本地事务是提交还是回滚
            joinPoint.proceed();
            
            //目标方法没有抛出异常  修改中间状态为COMMIT状态
            childTransaction.setTransactionalEnumStatusCode(TransactionalEnumStatus.COMMIT.getCode());
            mitGlobalTransactionManager.saveToRedis(childTransaction);
            
        } catch (Throwable throwable) {
            log.error("保存子事务状态到redis中抛出异常:globalId:{},childId:{},异常:{}", childTransaction.getGlobalTransactionalId(),
                    childTransaction.getChildTransactionalId(), throwable.getStackTrace());
            //调用本地事务方法异常的话,修改当前子事务状态为ROLLBACK状态
            childTransaction.setTransactionalEnumStatusCode(TransactionalEnumStatus.RollBACK.getCode());
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
    private ChildTransaction builderChildTransactionObj(Integer TransactionalTypeEunmCode,
            Integer TransationalEnumStatusCode) {
        MitTruncationBuilder mitTruncationBuilder = new MitTruncationBuilder();
        String childTransId = MitDtUtil.generatorChildTransactionalId();
        MitTransactionalHolder.setChild(childTransId);
        return mitTruncationBuilder.builderTransactionalTypeEnumCode(TransactionalTypeEunmCode)
                .builderChildTransactionalId(childTransId).builderTransactionalEnumStatus(TransationalEnumStatusCode)
                .builderGlobalTransactionId(MitTransactionalHolder.get()).builder();
    }
}
