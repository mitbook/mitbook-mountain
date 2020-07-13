package com.mitbook.support.holder;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author pengzhengfa
 */
@ConfigurationProperties(prefix = "mit.dt")
@Data
public class MitDtProperties {
    
    private long initialDelay = 1;
    
    private long delay = 1;
    
    private Integer watingTime = 5;
}
