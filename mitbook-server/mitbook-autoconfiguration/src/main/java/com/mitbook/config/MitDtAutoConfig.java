package com.mitbook.config;

import com.mitbook.core.MitGlobalTransactionManager;
import com.mitbook.support.aspect.MitConnectionAspect;
import com.mitbook.support.aspect.MitTransactionalAspect;
import com.mitbook.support.holder.MitDtProperties;
import com.mitbook.support.marker.MitConfigMarker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分布式事务配置类
 *
 * @author pengzhengfa
 */
@Configuration
@Slf4j
@EnableConfigurationProperties(MitDtProperties.class)
@ConditionalOnBean(MitConfigMarker.class)
public class MitDtAutoConfig {
    
    @Bean
    public MitConnectionAspect mitConnectionAspect() {
        log.info("加载MitConnectionAspect切面到容器中");
        return new MitConnectionAspect();
    }
    
    @Bean
    public MitTransactionalAspect mitTransactionalAspect() {
        log.info("加载MitTransactionalAspect切面到容器中");
        return new MitTransactionalAspect();
    }
    
    @Bean
    public MitGlobalTransactionManager mitGlobalTransactionManager() {
        log.info("加载MitGlobalTransactionManager到容器中");
        return new MitGlobalTransactionManager();
    }
}
