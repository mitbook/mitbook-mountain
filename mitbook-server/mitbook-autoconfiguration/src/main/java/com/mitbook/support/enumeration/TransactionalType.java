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

package com.mitbook.support.enumeration;

/**
 * 分布式事务中子事务状态描述枚举
 *
 * @author pengzhengfa
 */
public enum TransactionalType {
    
    BEGIN(1, "事务开始,需要创建事务组对象"),
    
    ADD(0, "添加到事务组中"),
    
    END(-1, "事务结束,需要计算事务组中各个子事务结果");
    
    public String getMsg() {
        return msg;
    }
    
    public Integer getCode() {
        
        return code;
    }
    
    private Integer code;
    
    private String msg;
    
    TransactionalType(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
