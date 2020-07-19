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
