package com.myretail.mrproductapi.converter;

import com.myretail.mrproductapi.domain.ProductPrice;
import org.springframework.core.convert.converter.Converter;

import java.util.Optional;

public interface ProductPriceResponseConverter<T> extends Converter<Optional<T>, Optional<ProductPrice>> {
}
