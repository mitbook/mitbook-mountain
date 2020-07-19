package com.mitbook.entity;

import lombok.Data;

/**
 * 商品库存
 *
 * @author pengzhengfa
 */
@Data
public class ProductStockInfo {
    
    private String productId;
    
    private String productName;
    
    private Integer stockNum;
}
