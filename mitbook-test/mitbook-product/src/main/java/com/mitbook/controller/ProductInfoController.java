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

import com.mitbook.service.IProductStockInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pengzhengfa
 */
@RestController
@RequestMapping("/product")
@Slf4j
public class ProductInfoController {
    
    @Autowired
    private IProductStockInfoService productStockInfoService;
    
    @RequestMapping("/reduceById/{productId}")
    public Object reduceProductById(@PathVariable("productId") String productId) {
        try {
            productStockInfoService.updateProductStock(productId);
            return "success";
        } catch (Exception e) {
            log.error("调用商品服务异常:{}", e);
            return "error";
        }
    }
}
