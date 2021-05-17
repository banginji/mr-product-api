package com.myretail.mrproductapi.service;

import com.myretail.mrproductapi.domain.ProductPrice;
import com.myretail.mrproductapi.domain.price.UpdatePriceInfo;
import com.myretail.mrproductapi.domain.price.UpdatePriceRequest;
import com.myretail.mrproductapi.domain.redsky.RedSkyResponse;
import com.myretail.mrproductapi.persistence.Price;
import com.myretail.mrproductapi.service.price.ProductPriceService;
import com.myretail.mrproductapi.service.title.ProductTitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductInfoServiceImpl implements ProductInfoService {
    private final ProductPriceService<Price, Integer> productPriceService;

    @Override
    public Optional<ProductPrice> getPrice(Integer id) {
        return productPriceService.getEntity(id);
    }

    private final ProductTitleService<RedSkyResponse, Integer> productTitleService;

    @Override
    public Optional<String> getTitle(Integer id) {
        return productTitleService.getEntity(id);
    }

    private final ProductPriceService<Price, UpdatePriceInfo> updatePriceService;

    @Override
    public void updatePrice(Integer id, UpdatePriceRequest updatePriceRequest) {
        updatePriceService.getEntity(new UpdatePriceInfo(id, updatePriceRequest.value(), updatePriceRequest.currencyCode()));
    }
}
