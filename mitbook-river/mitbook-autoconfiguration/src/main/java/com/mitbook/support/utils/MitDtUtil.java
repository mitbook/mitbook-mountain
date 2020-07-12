package com.mitbook.support.utils;

import java.util.UUID;

/**
 * @author pengzhengfa
 */
public class MitDtUtil {
    
    /**
     * 方法实现说明:生成事务全局id
     */
    public static String generatorGlobalTransactionalId() {
        return UUID.randomUUID().toString();
    }
    
    /**
     * 方法实现说明:生成子事务id
     */
    public static String generatorChildTransactionalId() {
        return UUID.randomUUID().toString();
    }
}

