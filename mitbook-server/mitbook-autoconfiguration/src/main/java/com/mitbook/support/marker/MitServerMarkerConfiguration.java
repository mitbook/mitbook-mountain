package com.mitbook.support.marker;

import org.springframework.context.annotation.Bean;

/**
 * 条件激活bean
 *
 * @author pengzhengfa
 */
public class MitServerMarkerConfiguration {
    
    @Bean
    public Marker mitServerMarkerBean() {
        return new Marker();
    }
    
    class Marker {
    
    }
}
