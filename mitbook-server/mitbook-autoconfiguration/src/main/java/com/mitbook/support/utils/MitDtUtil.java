package com.mitbook.support.utils;

import java.util.UUID;

/**
 * @author pengzhengfa
 */
public class MitDtUtil {
    
    /**
     * 生成事务全局id
     */
    public static String generatorGlobalTransactionalId() {
        return UUID.randomUUID().toString();
    }
    
    /**
     * 生成子事务id
     */
    public static String generatorChildTransactionalId() {
        return UUID.randomUUID().toString();
    }
}

