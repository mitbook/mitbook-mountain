package com.mitbook.support.holder;

/**
 * 线程局部变量持有器,用户同一个线程传递全局事务ID
 *
 * @author pengzhengfa
 */
public class MitTransactionalHolder {
    
    public static final ThreadLocal<String> GLOBALTRANSACTIONID = new ThreadLocal<>();
    
    public static final ThreadLocal<String> CHILDTRANSACTIONID = new ThreadLocal<>();
    
    public static void set(String globalTransactionId) {
        GLOBALTRANSACTIONID.set(globalTransactionId);
    }
    
    public static String get() {
        return GLOBALTRANSACTIONID.get();
    }
    
    public static void setChild(String globalTransactionId) {
        CHILDTRANSACTIONID.set(globalTransactionId);
    }
    
    public static String getChild() {
        return CHILDTRANSACTIONID.get();
    }
}
