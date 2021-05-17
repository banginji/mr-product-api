package com.myretail.mrproductapi.service;

import com.myretail.mrproductapi.domain.ProductPrice;
import com.myretail.mrproductapi.domain.price.UpdatePriceRequest;

import java.util.Optional;

public interface ProductInfoService {
    Optional<ProductPrice> getPrice(Integer id);
    Optional<String> getTitle(Integer id);
    void updatePrice(Integer id, UpdatePriceRequest updatePriceRequest);
}
