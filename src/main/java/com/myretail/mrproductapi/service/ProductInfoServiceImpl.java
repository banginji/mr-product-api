package com.myretail.mrproductapi.service;

import com.myretail.mrproductapi.domain.ProductPrice;
import com.myretail.mrproductapi.domain.redsky.RedSkyResponse;
import com.myretail.mrproductapi.persistence.Price;
import com.myretail.mrproductapi.service.price.AbstractProductPriceService;
import com.myretail.mrproductapi.service.title.AbstractProductTitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductInfoServiceImpl implements ProductInfoService {
    private final AbstractProductPriceService<Price> productPriceService;

    @Override
    public Optional<ProductPrice> getPrice(Integer id) {
        return productPriceService.getEntity(id);
    }

    private final AbstractProductTitleService<RedSkyResponse> productTitleService;

    @Override
    public Optional<String> getTitle(Integer id) {
        return productTitleService.getEntity(id);
    }
}
