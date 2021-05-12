package com.myretail.mrproductapi.converter;

import com.myretail.mrproductapi.domain.ProductPrice;
import com.myretail.mrproductapi.persistence.Price;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PriceResponseConverter implements Converter<Optional<Price>, Optional<ProductPrice>> {
    @Override
    public Optional<ProductPrice> convert(Optional<Price> source) {
        return source.map(price -> new ProductPrice(price.value(), price.currencyCode()));
    }
}
