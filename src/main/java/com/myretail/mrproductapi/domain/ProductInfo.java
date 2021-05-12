package com.myretail.mrproductapi.domain;

import java.util.Optional;

public record ProductInfo(Integer id, Optional<String> title, Optional<ProductPrice> price) {
    public static ProductInfo of(Integer id) {
        return new ProductInfo(id, Optional.empty(), Optional.empty());
    }
}
