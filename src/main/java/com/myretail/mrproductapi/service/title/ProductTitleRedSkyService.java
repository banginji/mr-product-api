package com.myretail.mrproductapi.service.title;

import com.myretail.mrproductapi.converter.ProductTitleResponseConverter;
import com.myretail.mrproductapi.domain.redsky.RedSkyResponse;
import com.myretail.mrproductapi.service.ProductInfoFetcherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductTitleRedSkyService extends ProductTitleService<RedSkyResponse, Integer> {
    private final RedSkyService redSkyService;

    @Override
    public ProductTitleResponseConverter<RedSkyResponse> converter() {
        return source -> source.map(response -> response.product().item().productDescription().title());
    }

    @Override
    public ProductInfoFetcherService<Integer, RedSkyResponse> fetcherService() {
        return redSkyService::findTitleData;
    }
}
