package com.myretail.mrproductapi.service;

import com.myretail.mrproductapi.domain.ProductPrice;

import java.util.Optional;

public interface ProductPriceService {
    Optional<ProductPrice> getPrice(Integer id);
}
