package com.mitbook.core;

import lombok.Data;

import java.io.Serializable;

/**
 * 分布式事务,子事务对象
 *
 * @author pengzhengfa
 */
@Data
public class ChildTransaction implements Serializable {
    
    /**
     * 子事务id
     */
    private String childTransactionalId;
    
    /**
     * 全局事务id
     */
    private String globalTransactionalId;
    
    /**
     * 子事务状态
     */
    private Integer TransactionalStatusCode;
    
    /**
     * 子事务类型
     */
    private Integer TransactionalTypeCode;
    
    
}
