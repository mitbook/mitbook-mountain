package com.mitbook.mapper;

import com.mitbook.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author pengzhengfa
 */
@Mapper
public interface OrderInfoMapper {
    
    /**
     * 保存订单信息
     *
     * @param order
     */
    void saveOrder(OrderInfo order);
}
