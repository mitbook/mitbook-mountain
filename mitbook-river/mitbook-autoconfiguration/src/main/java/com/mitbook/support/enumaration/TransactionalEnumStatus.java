package com.mitbook.support.enumaration;

/**
 * 分布式事务中,事务状态枚举描述
 *
 * @author pengzhengfa
 */
public enum TransactionalEnumStatus {

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

    TransactionalEnumStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static TransactionalEnumStatus getbycode(Integer code) {
        for (TransactionalEnumStatus transactionalEnumStatus : values()) {
            if (transactionalEnumStatus.getCode() == code) {
                return transactionalEnumStatus;
            }
        }
        return null;
    }
}
