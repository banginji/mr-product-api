package com.myretail.mrproductapi.service.price;

import com.myretail.mrproductapi.domain.ProductPrice;
import com.myretail.mrproductapi.persistence.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("unchecked")
class GetProductPriceServiceTest {
    DataSourceService dataSourceService;

    @BeforeEach
    void beforeEach() {
        dataSourceService = Mockito.mock(DataSourceService.class);
    }

    @Test
    void somePriceConversionTest() {
        Price price = new Price(1, 3.4, "USD");

        GetProductPriceService getProductPriceService = new GetProductPriceService(dataSourceService);

        Optional<ProductPrice> result = getProductPriceService.responseConverter().convert(Optional.of(price));

        Optional<ProductPrice> expected = Optional.of(new ProductPrice(price.value(), price.currencyCode()));

        // Assertions
        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void emptyPriceConversionTest() {
        Optional<Price> price = Optional.empty();

        GetProductPriceService getProductPriceService = new GetProductPriceService(dataSourceService);

        Optional<ProductPrice> result = getProductPriceService.responseConverter().convert(price);

        assertThat(result).isEmpty();
    }

    @Test
    void testWhenDataStoreDoesNotHavePriceInformationForGivenId() {
        Mockito.when(dataSourceService.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        GetProductPriceService getProductPriceService = new GetProductPriceService(dataSourceService);

        Optional<Price> actual = getProductPriceService.fetcherService().findEntity(1);

        // Assertions
        assertThat(actual).isEmpty();
    }

    @Test
    void testWhenDataStoreHasPriceInformationForGivenId() {
        Price price = new Price(1, 1.1, "USD");
        Mockito.when(dataSourceService.findById(Mockito.anyInt())).thenReturn(Optional.of(price));

        GetProductPriceService getProductPriceService = new GetProductPriceService(dataSourceService);

        Optional<Price> actual = getProductPriceService.fetcherService().findEntity(1);

        // Assertions
        assertThat(actual).isNotEmpty();
        assertThat(actual.get()).isEqualTo(price);
    }
}