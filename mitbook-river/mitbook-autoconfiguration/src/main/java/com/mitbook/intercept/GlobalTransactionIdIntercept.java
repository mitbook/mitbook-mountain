package com.mitbook.intercept;

import com.mitbook.support.holder.MitTransactionalHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局事务ID拦截器,从header中获取分布式全局id
 *
 * @author pengzhengfa
 */
@Slf4j
public class GlobalTransactionIdIntercept implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String globalTransactionId = request.getHeader("globalTransactionId");
        if (StringUtils.isEmpty(globalTransactionId)) {
            log.info("请求头未包含请求参数globalTransactionId:{}", globalTransactionId);
            return false;
        }
        MitTransactionalHolder.set(globalTransactionId);
        return true;
    }
}
