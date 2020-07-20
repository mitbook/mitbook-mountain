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
