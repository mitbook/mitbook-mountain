package com.mitbook.encryption;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author BobSimon
 */
@Component
public class SystemCheck implements ApplicationListener {

    private int cpuCount = Runtime.getRuntime().availableProcessors() * 2;

    private int checkCpu= 8;

    private String jdkVersion="1.8.0_212";

    private  Properties props=System.getProperties();

    private String runDir="/Users/pengzhengfa/Desktop/project/mitbook-mountain";

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (!SystemAuthCheck()){
            throw new RuntimeException("Sorry, the system has no permission to use, please contact the administrator!");
        }
    }
    public boolean SystemAuthCheck() {
        if (cpuCount != checkCpu && !props.getProperty("java.version").equals(jdkVersion) && !props.getProperty("user.dir").equals(runDir)) {
            throw new RuntimeException("Sorry, the system has no permission to use, please contact the administrator!");
        }
        return true;
    }
}
