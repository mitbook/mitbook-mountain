package com.mitbook.core;

/**
 * MitTruncationBuilder构建子事务对象
 *
 * @author pengzhengfa
 */
public class MitTruncationBuilder {
    
    private ChildTransaction childTransaction;
    
    public MitTruncationBuilder() {
        childTransaction = new ChildTransaction();
    }
    
    public MitTruncationBuilder builderChildTransactionalId(String childTransactionalId) {
        childTransaction.setChildTransactionalId(childTransactionalId);
        return this;
    }
    
    public MitTruncationBuilder builderTransactionalEnumStatus(Integer transactionalEnumStatusCode) {
        childTransaction.setTransactionalEnumStatusCode(transactionalEnumStatusCode);
        return this;
    }
    
    public MitTruncationBuilder builderTransactionalTypeEnumCode(Integer transactionalTypeEnumCode) {
        childTransaction.setTransactionalTypeEnumCode(transactionalTypeEnumCode);
        return this;
    }
    
    public MitTruncationBuilder builderGlobalTransactionId(String globalTransactionId) {
        childTransaction.setGlobalTransactionalId(globalTransactionId);
        return this;
    }
    
    public ChildTransaction builder() {
        return childTransaction;
    }
}
