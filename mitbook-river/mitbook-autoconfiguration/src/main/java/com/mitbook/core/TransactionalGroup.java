package com.mitbook.core;

import lombok.Data;

import java.util.List;

/**
 * 分布式事务组对象
 *
 * @author pengzhengfa
 */
@Data
public class TransactionalGroup {
    
    /**
     * 全局事务ID
     */
    private String globalTransactionalId;
    
    /**
     * 该事务组下所有子事务集合
     */
    private List<ChildTransaction> childTransactions;
}
