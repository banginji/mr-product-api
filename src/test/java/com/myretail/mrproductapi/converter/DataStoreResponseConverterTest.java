package com.myretail.mrproductapi.converter;

import com.myretail.mrproductapi.domain.ProductPrice;
import com.myretail.mrproductapi.persistence.Price;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class DataStoreResponseConverterTest {
    @Test
    void somePriceConversionTest() {
        Price price = new Price(1, 3.4, "USD");
        DataStoreResponseConverter converter = new DataStoreResponseConverter();
        Optional<ProductPrice> result = converter.convert(Optional.of(price));

        Optional<ProductPrice> expected = Optional.of(new ProductPrice(price.value(), price.currencyCode()));
        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void emptyPriceConversionTest() {
        Optional<Price> price = Optional.empty();
        DataStoreResponseConverter converter = new DataStoreResponseConverter();
        Optional<ProductPrice> result = converter.convert(price);

        assertThat(result).isEmpty();
    }
}