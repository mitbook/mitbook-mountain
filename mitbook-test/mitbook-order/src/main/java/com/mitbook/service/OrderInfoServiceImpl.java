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

package com.mitbook.service;

import com.mitbook.entity.OrderInfo;
import com.mitbook.mapper.OrderInfoMapper;
import com.mitbook.support.anno.GlobalTransactional;
import com.mitbook.support.enumeration.TransactionalType;
import com.mitbook.support.holder.TransactionalHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * @author pengzhengfa
 */
@Service
@Slf4j
public class OrderInfoServiceImpl implements IOrderInfoService {
    
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    
    private static final String url = "http://localhost:8088/product/reduceById/";
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Override
    @Transactional
    @GlobalTransactional(transType = TransactionalType.BEGIN)
    public void saveOrder(OrderInfo order) {
        log.info("implementation target method");
        orderInfoMapper.saveOrder(order);
        
        //构建请求头
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("globalTransactionId", TransactionalHolder.get());
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        //发送请求
        ResponseEntity<String> response = restTemplate
                .exchange(url + order.getProductId(), HttpMethod.GET, requestEntity, String.class);
        
        if ("error".equals(response.getBody())) {
            throw new RuntimeException("exception calling remote service" + url + order.getProductId());
        }
    }
}
