package com.mitbook.service;

import com.mitbook.mapper.ProductInfoMapper;
import com.mitbook.support.anno.GlobalTransactional;
import com.mitbook.support.enumeration.TransactionalType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author pengzhengfa
 */
@Service
public class ProductStockServiceImpl implements IProductStockInfoService {
    
    @Autowired
    ProductInfoMapper productInfoMapper;
    
    @Override
    @Transactional
    @GlobalTransactional(transType = TransactionalType.END)
    public void updateProductStock(String productId) {
        productInfoMapper.updateProductInfo(productId);
    }
}
