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

package com.mitbook.support.holder;

/**
 * 线程局部变量持有器,用户同一个线程传递全局事务id
 *
 * @author pengzhengfa
 */
public class TransactionalHolder {
    
    public static final ThreadLocal<String> GLOBALTRANSACTIONID = new ThreadLocal<>();
    
    public static final ThreadLocal<String> CHILDTRANSACTIONID = new ThreadLocal<>();
    
    public static void set(String globalTransactionId) {
        GLOBALTRANSACTIONID.set(globalTransactionId);
    }
    
    public static String get() {
        return GLOBALTRANSACTIONID.get();
    }
    
    public static void setChild(String globalTransactionId) {
        CHILDTRANSACTIONID.set(globalTransactionId);
    }
    
    public static String getChild() {
        return CHILDTRANSACTIONID.get();
    }
}
