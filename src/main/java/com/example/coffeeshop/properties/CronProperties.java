package com.example.coffeeshop.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cron")
public class CronProperties {

    private String shopOpeningTime;

    public String getShopOpeningTime() {
        return shopOpeningTime;
    }

    public void setShopOpeningTime(String shopOpeningTime) {
        this.shopOpeningTime = shopOpeningTime;
    }
}
