package com.myretail.mrproductapi.service.price;

import com.myretail.mrproductapi.converter.ProductPriceResponseConverter;
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
    void testWhenDataStoreDoesNotHavePriceInformationForGivenId() {
        Mockito.when(priceRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        DataStoreService dataStoreService = new DataStoreService(priceRepository, (ProductPriceResponseConverter<Price>) Mockito.mock(ProductPriceResponseConverter.class));

        Optional<Price> actual = dataStoreService.findEntity(1);

        // Assertions
        assertThat(actual).isEmpty();
    }

    @Test
    void testWhenDataStoreHasPriceInformationForGivenId() {
        Price price = new Price(1, 1.1, "USD");
        Mockito.when(priceRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(price));

        DataStoreService dataStoreService = new DataStoreService(priceRepository, (ProductPriceResponseConverter<Price>) Mockito.mock(ProductPriceResponseConverter.class));

        Optional<Price> actual = dataStoreService.findEntity(1);

        // Assertions
        assertThat(actual).isNotEmpty();
        assertThat(actual.get()).isEqualTo(price);
    }
}