package com.mitbook.support.enumeration;

/**
 * 分布式事务中,事务状态枚举描述
 *
 * @author pengzhengfa
 */
public enum TransactionalStatus {
    
    COMMIT(1, "COMMIT"),
    
    WAITING(0, "WAITING"),
    
    RollBACK(-1, "RollBACK");
    
    public Integer getCode() {
        return code;
    }
    
    public String getMsg() {
        return msg;
    }
    
    private Integer code;
    
    private String msg;
    
    TransactionalStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public static TransactionalStatus getByCode(Integer code) {
        for (TransactionalStatus transactionalStatus : values()) {
            if (transactionalStatus.getCode() == code) {
                return transactionalStatus;
            }
        }
        return null;
    }
}
