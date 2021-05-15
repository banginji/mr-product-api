package com.myretail.mrproductapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
@EnableRetry
public class RestConfig {
    @Value("${redsky.host}")
    String host;

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .rootUri(host)
                .setConnectTimeout(Duration.of(500, ChronoUnit.MILLIS))
                .setReadTimeout(Duration.of(500, ChronoUnit.MILLIS))
                .build();
    }
}
