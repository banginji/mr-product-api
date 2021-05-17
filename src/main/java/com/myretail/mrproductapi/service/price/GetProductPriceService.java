package com.myretail.mrproductapi.service.price;

import com.myretail.mrproductapi.converter.ProductPriceResponseConverter;
import com.myretail.mrproductapi.domain.ProductPrice;
import com.myretail.mrproductapi.persistence.Price;
import com.myretail.mrproductapi.service.ProductInfoFetcherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetProductPriceService extends ProductPriceService<Price, Integer> {
    private final DataSourceService dataSourceService;

    @Override
    public ProductPriceResponseConverter<Price> responseConverter() {
        return source -> source.map(price -> new ProductPrice(price.value(), price.currencyCode()));
    }

    @Override
    public ProductInfoFetcherService<Optional<Price>, Integer> fetcherService() {
        return dataSourceService::findById;
    }
}
