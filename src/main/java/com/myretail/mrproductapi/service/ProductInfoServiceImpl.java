package com.myretail.mrproductapi.service;

import com.myretail.mrproductapi.domain.ProductPrice;
import com.myretail.mrproductapi.domain.price.UpdatePriceInfo;
import com.myretail.mrproductapi.domain.price.UpdatePriceRequest;
import com.myretail.mrproductapi.domain.redsky.RedSkyResponse;
import com.myretail.mrproductapi.persistence.Price;
import com.myretail.mrproductapi.service.price.ProductPriceFetcherService;
import com.myretail.mrproductapi.service.price.ProductPriceUpdateService;
import com.myretail.mrproductapi.service.title.ProductTitleFetcherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductInfoServiceImpl implements ProductInfoService {
    private final ProductPriceFetcherService<Price, Integer> productPriceFetcherService;

    @Override
    public Optional<ProductPrice> getPrice(Integer id) {
        return productPriceFetcherService.getEntity(id);
    }

    private final ProductTitleFetcherService<RedSkyResponse, Integer> productTitleFetcherService;

    @Override
    public Optional<String> getTitle(Integer id) {
        return productTitleFetcherService.getEntity(id);
    }

    private final ProductPriceUpdateService<Price> productPriceUpdateService;

    @Override
    public void updatePrice(Integer id, UpdatePriceRequest updatePriceRequest) {
        productPriceUpdateService.updateEntityIfFound(new UpdatePriceInfo(id, updatePriceRequest.value(), updatePriceRequest.currencyCode()));
    }
}
