package com.mitbook.support.enumaration;
/**
 * 分布式事务中子事务状态描述枚举
 * @author pengzhengfa
 */
public enum TransactionalTypeEnum {
    
    BEGIN(1,"事务开始,需要创建事务组对象"),
    
    ADD(0,"添加到事务组中"),
    
    END(-1,"事务结束,需要计算事务组中各个子事务结果");

    public String getMsg() {
        return msg;
    }

    public Integer getCode() {

        return code;
    }

    private Integer code;

    private String msg;

    TransactionalTypeEnum(Integer code, String msg){
        this.code=code;
        this.msg=msg;
    }
}
