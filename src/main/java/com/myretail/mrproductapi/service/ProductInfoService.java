package com.myretail.mrproductapi.service;

import com.myretail.mrproductapi.domain.ProductPrice;
import com.myretail.mrproductapi.domain.price.UpdatePriceInfo;

import java.util.Optional;

public interface ProductInfoService {
    Optional<ProductPrice> getPrice(Integer id);
    Optional<String> getTitle(Integer id);
    Optional<ProductPrice> updatePrice(UpdatePriceInfo updatePriceInfo);
}
