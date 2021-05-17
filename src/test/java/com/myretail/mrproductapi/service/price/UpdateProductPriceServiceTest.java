package com.myretail.mrproductapi.service.price;

import com.myretail.mrproductapi.domain.ProductPrice;
import com.myretail.mrproductapi.domain.price.UpdatePriceInfo;
import com.myretail.mrproductapi.persistence.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateProductPriceServiceTest {
    DataSourceService dataSourceService;

    @BeforeEach
    void beforeEach() {
        dataSourceService = Mockito.mock(DataSourceService.class);
    }

    @Test
    void somePriceConversionTest() {
        Price price = new Price(1, 3.4, "USD");

        UpdateProductPriceService updateProductPriceService = new UpdateProductPriceService(dataSourceService);

        Optional<ProductPrice> result = updateProductPriceService.responseConverter().convert(Optional.of(price));

        Optional<ProductPrice> expected = Optional.of(new ProductPrice(price.value(), price.currencyCode()));

        // Assertions
        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void emptyPriceConversionTest() {
        Optional<Price> price = Optional.empty();

        UpdateProductPriceService updateProductPriceService = new UpdateProductPriceService(dataSourceService);

        Optional<ProductPrice> result = updateProductPriceService.responseConverter().convert(price);

        assertThat(result).isEmpty();
    }

    @Test
    void testWhenDataStoreDoesNotHavePriceInformationForGivenId() {
        Mockito.when(dataSourceService.patchUpdate(Mockito.any(UpdatePriceInfo.class))).thenReturn(Optional.empty());

        UpdateProductPriceService updateProductPriceService = new UpdateProductPriceService(dataSourceService);

        UpdatePriceInfo updatePriceInfo = new UpdatePriceInfo(1, Optional.empty(), Optional.empty());
        Optional<Price> actual = updateProductPriceService.fetcherService().findEntity(updatePriceInfo);

        // Assertions
        assertThat(actual).isEmpty();
    }

    @Test
    void testWhenDataStoreHasPriceInformationForGivenId() {
        Price price = new Price(1, 1.1, "USD");
        Mockito.when(dataSourceService.patchUpdate(Mockito.any(UpdatePriceInfo.class))).thenReturn(Optional.of(price));

        UpdateProductPriceService updateProductPriceService = new UpdateProductPriceService(dataSourceService);

        UpdatePriceInfo updatePriceInfo = new UpdatePriceInfo(1, Optional.empty(), Optional.empty());
        Optional<Price> actual = updateProductPriceService.fetcherService().findEntity(updatePriceInfo);

        // Assertions
        assertThat(actual).isNotEmpty();
        assertThat(actual.get()).isEqualTo(price);
    }
}