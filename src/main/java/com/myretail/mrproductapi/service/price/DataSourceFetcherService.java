package com.myretail.mrproductapi.service.price;

import com.myretail.mrproductapi.domain.ProductPrice;
import com.myretail.mrproductapi.persistence.Price;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataSourceFetcherService extends ProductPriceFetcherService<Price, Integer> {
    private final DataSourceService dataSourceService;

    @Override
    public Optional<Price> findEntity(Integer id) {
        return dataSourceService.findById(id);
    }

    @Override
    public Optional<ProductPrice> convert(Optional<Price> source) {
        return source.map(price -> new ProductPrice(price.value(), price.currencyCode()));
    }
}
