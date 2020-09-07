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

package com.mitbook.intercept;

import com.mitbook.support.holder.TransactionalHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 全局事务id拦截器,从header中获取分布式全局id
 *
 * @author pengzhengfa
 */
@Slf4j
public class GlobalTransactionIdIntercept implements HandlerInterceptor {
    
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            String globalTransactionId = request.getHeader("globalTransactionId");
            if (StringUtils.isEmpty(globalTransactionId)) {
                log.info("the request header does not contain the request parameter globalTransactionId:{}",
                        globalTransactionId);
                return false;
            }
            TransactionalHolder.set(globalTransactionId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
