package com.azgroup.bookstore.configuration;

import com.azgroup.bookstore.service.AccessControlService;
import com.azgroup.bookstore.service.impl.AccessControlServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccessControlConfig {
    @Bean
    public AccessControlService accessControlService() {
        return new AccessControlServiceImpl();
    }
}
