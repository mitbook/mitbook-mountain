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
 * 分布式事务中,事务状态枚举描述
 *
 * @author pengzhengfa
 */
public enum TransactionalStatus {
    
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
    
    TransactionalStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public static TransactionalStatus getByCode(Integer code) {
        
        for (TransactionalStatus transactionalStatus : values()) {
            Integer statusCode = transactionalStatus.getCode();
            if (statusCode == code) {
                return transactionalStatus;
            }
        }
        return null;
    }
}
