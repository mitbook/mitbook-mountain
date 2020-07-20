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
package com.mitbook.controller;

import com.mitbook.entity.OrderInfo;
import com.mitbook.service.IOrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pengzhengfa
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderInfoController {
    
    @Autowired
    private IOrderInfoService orderInfoService;
    
    @RequestMapping("/saveOrder")
    public Map saveOrder(OrderInfo orderInfo) {
        Map<String, Object> retMap = new HashMap<>();
        try {
            orderInfoService.saveOrder(orderInfo);
            retMap.put("code", "1");
            retMap.put("msg", "保存成功");
            
        } catch (Exception e) {
            log.error("保存订单异常:{}", e);
            retMap.put("code", "-1");
            retMap.put("msg", "保存失败");
        }
        return retMap;
    }
}
