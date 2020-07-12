package com.mitbook.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @author pengzhengfa
 */
@Mapper
public interface ProductInfoMapper {
    
    @Update("update product_stock_info set stock_num=stock_num-1 where product_id=#{productId}")
    public void updateProductInfo(String productId);
}
