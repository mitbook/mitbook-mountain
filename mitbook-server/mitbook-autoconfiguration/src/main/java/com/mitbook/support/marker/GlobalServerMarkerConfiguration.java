package com.mitbook.support.marker;

import org.springframework.context.annotation.Bean;

/**
 * 条件激活bean
 *
 * @author pengzhengfa
 */
public class GlobalServerMarkerConfiguration {
    
    @Bean
    public Marker globalServerMarkerBean() {
        return new Marker();
    }
}
