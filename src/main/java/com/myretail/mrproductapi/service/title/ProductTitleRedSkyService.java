package com.myretail.mrproductapi.service.title;

import com.myretail.mrproductapi.converter.ProductTitleResponseConverter;
import com.myretail.mrproductapi.domain.redsky.RedSkyResponse;
import com.myretail.mrproductapi.service.ProductInfoFetcherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductTitleRedSkyService extends ProductTitleService<RedSkyResponse, Integer> {
    private final RedSkyService redSkyService;

    @Override
    public ProductTitleResponseConverter<RedSkyResponse> responseConverter() {
        return source -> source.map(response -> response.product().item().productDescription().title());
    }

    @Override
    public ProductInfoFetcherService<Optional<RedSkyResponse>, Integer> fetcherService() {
        return redSkyService::findTitleData;
    }
}
