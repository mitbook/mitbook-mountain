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
