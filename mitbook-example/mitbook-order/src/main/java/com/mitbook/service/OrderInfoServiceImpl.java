package com.mitbook.service;

import com.mitbook.entity.OrderInfo;
import com.mitbook.mapper.OrderInfoMapper;
import com.mitbook.support.anno.MitTransactional;
import com.mitbook.support.enumaration.TransactionalTypeEnum;
import com.mitbook.support.holder.MitTransactionalHolder;
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
    @MitTransactional(transType = TransactionalTypeEnum.BEGIN)
    public void saveOrder(OrderInfo order) {
        log.info("执行目标方法");
        orderInfoMapper.saveOrder(order);
        
        //构建请求头
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("globalTransactionId", MitTransactionalHolder.get());
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        //发送请求
        ResponseEntity<String> response = restTemplate
                .exchange(url + order.getProductId(), HttpMethod.GET, requestEntity, String.class);
        
        if ("error".equals(response.getBody())) {
            throw new RuntimeException("调用远程服务异常" + url + order.getProductId());
        }
//        System.out.println(1/0);
    }
}
