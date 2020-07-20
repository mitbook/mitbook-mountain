/*
 * Copyright 1999-2020 Mitbook Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mitbook.core;

/**
 * GlobalTruncationBuilder构建子事务对象
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
