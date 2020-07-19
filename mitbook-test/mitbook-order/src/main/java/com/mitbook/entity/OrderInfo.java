package com.mitbook.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author pengzhengfa
 */
@Data
public class OrderInfo {
    
    private String orderNo;
    
    private Integer userId;
    
    private Date createDate = new Date();
    
    private String productId;
    
    private String productNum;
}
