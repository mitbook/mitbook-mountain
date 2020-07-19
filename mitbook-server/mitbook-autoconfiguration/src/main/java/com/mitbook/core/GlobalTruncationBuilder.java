package com.mitbook.core;

/**
 * MitTruncationBuilder构建子事务对象
 *
 * @author pengzhengfa
 */
public class GlobalTruncationBuilder {
    
    private ChildTransaction childTransaction;
    
    public GlobalTruncationBuilder() {
        childTransaction = new ChildTransaction();
    }
    
    public GlobalTruncationBuilder builderChildTransactionalId(String childTransactionalId) {
        childTransaction.setChildTransactionalId(childTransactionalId);
        return this;
    }
    
    public GlobalTruncationBuilder builderTransactionalEnumStatus(Integer transactionalEnumStatusCode) {
        childTransaction.setTransactionalStatusCode(transactionalEnumStatusCode);
        return this;
    }
    
    public GlobalTruncationBuilder builderTransactionalTypeEnumCode(Integer transactionalTypeEnumCode) {
        childTransaction.setTransactionalTypeCode(transactionalTypeEnumCode);
        return this;
    }
    
    public GlobalTruncationBuilder builderGlobalTransactionId(String globalTransactionId) {
        childTransaction.setGlobalTransactionalId(globalTransactionId);
        return this;
    }
    
    public ChildTransaction builder() {
        return childTransaction;
    }
}
