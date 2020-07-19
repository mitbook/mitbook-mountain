package com.mitbook.support.holder;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author pengzhengfa
 */
@ConfigurationProperties(prefix = "transactional.dt")
@Data
public class TransactionalProperties {
    
    private long initialDelay = 1;
    
    private long delay = 1;
    
    private Integer waitingTime = 5;
}
