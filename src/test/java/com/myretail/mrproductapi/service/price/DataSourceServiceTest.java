package com.myretail.mrproductapi.service.price;

import com.myretail.mrproductapi.domain.price.UpdatePriceInfo;
import com.myretail.mrproductapi.persistence.Price;
import com.myretail.mrproductapi.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class DataSourceServiceTest {
    private PriceRepository priceRepository;

    @BeforeEach
    void beforeEach() {
        priceRepository = Mockito.mock(PriceRepository.class);
    }

    @Test
    void testFindByIdIsEmptyWhenPriceIsNotFound() {
        Mockito.when(priceRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        DataSourceService dataSourceService = new DataSourceServiceImpl(priceRepository);

        Optional<Price> actual = dataSourceService.findById(1);

        // Assertions
        assertThat(actual).isEmpty();
    }

    @Test
    void testFindByIdReturnsPriceIfFound() {
        Price price = new Price(1, 1.1, "USD");
        Mockito.when(priceRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(price));

        DataSourceService dataSourceService = new DataSourceServiceImpl(priceRepository);

        Optional<Price> actual = dataSourceService.findById(1);

        // Assertions
        assertThat(actual).isNotEmpty();
        assertThat(actual.get()).isEqualTo(price);
    }

    @Test
    void testPatchUpdateIsEmptyWhenPriceIsNotFound() {
        Mockito.when(priceRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        DataSourceService dataSourceService = new DataSourceServiceImpl(priceRepository);

        Optional<Price> actual = dataSourceService.patchUpdate(new UpdatePriceInfo(1, Optional.empty(), Optional.empty()));

        // Assertions
        assertThat(actual).isEmpty();
    }

    @Test
    void testPatchUpdateUpdatesRelevantFieldsPriceIfFound() {
        Price price = new Price(1, 1.1, "USD");
        Mockito.when(priceRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(price));

        DataSourceService dataSourceService = new DataSourceServiceImpl(priceRepository);

        Double newValue = 2.2;
        Price newPrice = new Price(1, newValue, "USD");
        Mockito.when(priceRepository.save(Mockito.any(Price.class))).thenReturn(newPrice);

        UpdatePriceInfo updatePriceInfo = new UpdatePriceInfo(1, Optional.of(newValue), Optional.empty());
        Optional<Price> actual = dataSourceService.patchUpdate(updatePriceInfo);

        // Assertions
        assertThat(actual).isNotEmpty();
        assertThat(actual.get().value()).isEqualTo(newValue);
        assertThat(actual.get().currencyCode()).isEqualTo(price.currencyCode());
    }
}