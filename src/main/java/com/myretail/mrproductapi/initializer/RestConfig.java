package com.myretail.mrproductapi.initializer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableRetry
public class RestConfig {
    @Value("${redsky.host:https://redsky.target.com}")
    String host;

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.rootUri(host).build();
    }
}
