package com.mitbook.core;
import com.alibaba.fastjson.JSONObject;
import com.mitbook.support.enumaration.TransactionalTypeEnum;
import com.mitbook.support.enumaration.TransactionalEnumStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
/**
 * 分布式事务管理器
 * @author pengzhengfa
 */
@Slf4j
public class MitGlobalTransactionManager {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 全局事务和子事务映射关系
     */
    public static final String GLOBAL_TRANSACTION_MAPPING_CHILDE_TRANSACTIONAL_CACHE_KEY = "ANGLE_DIST_TRANSACTIONAL:";


    /**
     * 计算各个子事务是否能够被提交
     * 那么需要计算分布式事务 是提交还是回滚
     * @param globalTransactionId
     * @exception: 可能抛出redis操作存储异常
     */
    public Integer calChildTransactionStatus(String globalTransactionId) {

        //获取hashKey 下的所有filed value
        Map<String,String> childTransMap =redisTemplate.opsForHash().entries(generatorHashKey(globalTransactionId));
        Set<String> childTransIdSet = childTransMap.keySet();
        Iterator<String> iterator = childTransIdSet.iterator();

        //用户统计各个子事务commit的提交个数
        Integer commitCount =0;

        boolean needRoolBack = false;

        Integer beginAndEnd =0;

        while (iterator.hasNext()) {
            String childTransId = iterator.next();
            log.info("transactionalInfo:{}",childTransMap.get(childTransId));
            ChildTransaction childTransaction = JSONObject.parseObject(childTransMap.get(childTransId), ChildTransaction.class);

            //只要子事务其中一个出现rollback,分布式事务回滚
            if(childTransaction.getTransactionalEnumStatusCode() == TransactionalEnumStatus.RollBACK.getCode()) {
                needRoolBack = true;
            }
            //统计commit的个数
            if(childTransaction.getTransactionalEnumStatusCode() == TransactionalEnumStatus.COMMIT.getCode()) {
                commitCount++;
            }

            if(childTransaction.getTransactionalTypeEnumCode() == TransactionalTypeEnum.BEGIN.getCode()||
                    childTransaction.getTransactionalTypeEnumCode() == TransactionalTypeEnum.END.getCode()) {
                beginAndEnd++;
            }

        }

        //没有收到begin 和end 的二个子事务
        if(beginAndEnd!=2) {
            return TransactionalEnumStatus.WAITING.getCode();
        }

        //收到了begin 和end的子事务对象，但是其中有出现了rollabck,全局回滚
        if(needRoolBack) {
            return TransactionalEnumStatus.RollBACK.getCode();
        }

        //若所有的子事务都是commit的
        if(childTransMap.size() == commitCount) {
            return TransactionalEnumStatus.COMMIT.getCode();
        }

        return TransactionalEnumStatus.WAITING.getCode();
    }




    /**
     * 方法实现说明:把子事务对象保存到redis
     * @param childTransaction 子事务对象
     * @exception: 可能抛出redis操作存储异常
     */
    public void save2Redis(ChildTransaction childTransaction) throws Exception{

        //从子事务中获取全局事务ID
        String globalTransationalId = childTransaction.getGlobalTransactionalId();

        //把事务对象保存到redis中
        redisTemplate.opsForHash().put(generatorHashKey(globalTransationalId), childTransaction.getChildTransactionalId(),JSONObject.toJSON(childTransaction).toString());

    }

    /**
     * @param globalTransactionId 全局事务id
     * @return: redis hash结构的的   hashKey
     */
    private String generatorHashKey(String globalTransactionId) {
        return GLOBAL_TRANSACTION_MAPPING_CHILDE_TRANSACTIONAL_CACHE_KEY+globalTransactionId;
    }


}
