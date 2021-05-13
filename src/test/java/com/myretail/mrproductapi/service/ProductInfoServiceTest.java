package com.myretail.mrproductapi.service;

import com.myretail.mrproductapi.domain.ProductPrice;
import com.myretail.mrproductapi.domain.redsky.RedSkyResponse;
import com.myretail.mrproductapi.persistence.Price;
import com.myretail.mrproductapi.service.price.ProductPriceService;
import com.myretail.mrproductapi.service.title.ProductTitleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("unchecked")
class ProductInfoServiceTest {
    ProductPriceService<Price> productPriceService;
    ProductTitleService<RedSkyResponse> productTitleService;

    @BeforeEach
    void beforeEach() {
        productPriceService = (ProductPriceService<Price>) Mockito.mock(ProductPriceService.class);
        productTitleService = (ProductTitleService<RedSkyResponse>) Mockito.mock(ProductTitleService.class);
    }

    @Test
    void testWhenNoPriceIsPresentForGivenIdInDataStore() {
        Mockito.when(productPriceService.getEntity(Mockito.anyInt())).thenReturn(Optional.empty());
        ProductInfoService productInfoService = new ProductInfoServiceImpl(productPriceService, productTitleService);
        Optional<ProductPrice> actual = productInfoService.getPrice(1);

        // Assertions
        assertThat(actual).isEmpty();
    }

    @Test
    void testWhenPriceIsPresentForGivenIdInDataStore() {
        ProductPrice productPrice = new ProductPrice(1.1, "USD");
        Mockito.when(productPriceService.getEntity(Mockito.anyInt())).thenReturn(Optional.of(productPrice));
        ProductInfoService productInfoService = new ProductInfoServiceImpl(productPriceService, productTitleService);
        Optional<ProductPrice> actual = productInfoService.getPrice(1);

        // Assertions
        assertThat(actual).isNotEmpty();
        assertThat(actual.get().value()).isEqualTo(productPrice.value());
        assertThat(actual.get().currencyCode()).isEqualTo(productPrice.currencyCode());
    }

    @Test
    void testWhenNoTitleIsPresentForGivenIdInRedSky() {
        Mockito.when(productTitleService.getEntity(Mockito.anyInt())).thenReturn(Optional.empty());
        ProductInfoService productInfoService = new ProductInfoServiceImpl(productPriceService, productTitleService);
        Optional<String> actual = productInfoService.getTitle(1);

        // Assertions
        assertThat(actual).isEmpty();
    }

    @Test
    void testWhenTitleIsPresentForGivenIdInRedSky() {
        String title = "someTitle";
        Mockito.when(productTitleService.getEntity(Mockito.anyInt())).thenReturn(Optional.of(title));
        ProductInfoService productInfoService = new ProductInfoServiceImpl(productPriceService, productTitleService);
        Optional<String> actual = productInfoService.getTitle(1);

        // Assertions
        assertThat(actual).isNotEmpty();
        assertThat(actual.get()).isEqualTo(title);
    }
}