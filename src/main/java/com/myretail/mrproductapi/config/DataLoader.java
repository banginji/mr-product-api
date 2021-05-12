package com.myretail.mrproductapi.config;

import com.myretail.mrproductapi.persistence.Price;
import com.myretail.mrproductapi.repository.PriceRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataLoader {
    @Bean
    ApplicationListener<ApplicationReadyEvent> ready(PriceRepository priceRepository) {
        return args -> {
            priceRepository.deleteAll();
            var prices = List.of(
                    new Price(123, 14.23, "USD"),
                    new Price(234, 74.24, "USD"),
                    new Price(345, 593.53, "EUR"),
                    new Price(456, 53.55, "USD"),
                    new Price(13860428, 1193.33, "USD"),
                    new Price(567, 93.33, "EUR")
            );
            priceRepository.saveAll(prices);
        };
    }
}
