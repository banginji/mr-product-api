package com.myretail.mrproductapi.service.price;

import com.myretail.mrproductapi.domain.ProductPrice;
import com.myretail.mrproductapi.persistence.Price;
import com.myretail.mrproductapi.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("unchecked")
class DataStoreServiceTest {
    PriceRepository priceRepository;

    @BeforeEach
    void beforeEach() {
        priceRepository = Mockito.mock(PriceRepository.class);
    }

    @Test
    void somePriceConversionTest() {
        Price price = new Price(1, 3.4, "USD");

        DataStoreService dataStoreService = new DataStoreService(priceRepository);

        Optional<ProductPrice> result = dataStoreService.converter().convert(Optional.of(price));

        Optional<ProductPrice> expected = Optional.of(new ProductPrice(price.value(), price.currencyCode()));

        // Assertions
        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void emptyPriceConversionTest() {
        Optional<Price> price = Optional.empty();

        DataStoreService dataStoreService = new DataStoreService(priceRepository);

        Optional<ProductPrice> result = dataStoreService.converter().convert(price);

        assertThat(result).isEmpty();
    }

    @Test
    void testWhenDataStoreDoesNotHavePriceInformationForGivenId() {
        Mockito.when(priceRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        DataStoreService dataStoreService = new DataStoreService(priceRepository);

        Optional<Price> actual = dataStoreService.fetcherService().findEntity(1);

        // Assertions
        assertThat(actual).isEmpty();
    }

    @Test
    void testWhenDataStoreHasPriceInformationForGivenId() {
        Price price = new Price(1, 1.1, "USD");
        Mockito.when(priceRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(price));

        DataStoreService dataStoreService = new DataStoreService(priceRepository);

        Optional<Price> actual = dataStoreService.fetcherService().findEntity(1);

        // Assertions
        assertThat(actual).isNotEmpty();
        assertThat(actual.get()).isEqualTo(price);
    }
}