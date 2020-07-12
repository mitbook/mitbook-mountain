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
    
    public MitTruncationBuilder builderTransationalEnumStatus(Integer transationalEnumStatusCode) {
        childTransaction.setTransactionalEnumStatusCode(transationalEnumStatusCode);
        return this;
    }
    
    public MitTruncationBuilder buliderTransactionalTypeEunmCode(Integer transactionalTypeEunmCode) {
        childTransaction.setTransactionalTypeEnumCode(transactionalTypeEunmCode);
        return this;
    }
    
    public MitTruncationBuilder buliderGlobalTransactionId(String globalTransactionId) {
        childTransaction.setGlobalTransactionalId(globalTransactionId);
        return this;
    }
    
    public ChildTransaction builder() {
        return childTransaction;
    }
}
