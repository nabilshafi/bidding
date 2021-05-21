package com.example.bidding;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * This class determine if the required property is set or not
 */
@Component
public class ConfigurationGuard implements InitializingBean {

    @Value("${bidders.urls}")
    private String bidders;

    public void afterPropertiesSet() {
        if (this.bidders == null ||  this.bidders.equals("${bidders.urls}")) {
            throw new IllegalArgumentException("${bidders} must be configured");
        }
    }
}