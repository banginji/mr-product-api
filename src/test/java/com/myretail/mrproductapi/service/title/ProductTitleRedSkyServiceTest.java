package com.myretail.mrproductapi.service.title;

import com.myretail.mrproductapi.converter.ProductTitleResponseConverter;
import com.myretail.mrproductapi.domain.redsky.RedSkyProduct;
import com.myretail.mrproductapi.domain.redsky.RedSkyProductItem;
import com.myretail.mrproductapi.domain.redsky.RedSkyProductItemDesc;
import com.myretail.mrproductapi.domain.redsky.RedSkyResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("unchecked")
class ProductTitleRedSkyServiceTest {
    RedSkyService redSkyService;
    @BeforeEach
    void beforeEach() {
        redSkyService = Mockito.mock(RedSkyService.class);
    }

    @Test
    void testWhenRedSkyDoesNotHaveTitleDataForGivenId() {
        Mockito.when(redSkyService.findTitleData(Mockito.anyInt())).thenReturn(Optional.empty());

        ProductTitleRedSkyService productTitleRedSkyService = new ProductTitleRedSkyService((ProductTitleResponseConverter<RedSkyResponse>) Mockito.mock(ProductTitleResponseConverter.class), redSkyService);

        Optional<RedSkyResponse> actual = productTitleRedSkyService.findEntity(1);

        // Assertions
        assertThat(actual).isEmpty();
    }

    @Test
    void testWhenRedSkyHasTitleDataForGivenId() {
        String title = "t1";
        RedSkyResponse redSkyResponse = new RedSkyResponse(new RedSkyProduct(new RedSkyProductItem("1", new RedSkyProductItemDesc(title))));
        Mockito.when(redSkyService.findTitleData(Mockito.anyInt())).thenReturn(Optional.of(redSkyResponse));

        ProductTitleRedSkyService productTitleRedSkyService = new ProductTitleRedSkyService((ProductTitleResponseConverter<RedSkyResponse>) Mockito.mock(ProductTitleResponseConverter.class), redSkyService);

        Optional<RedSkyResponse> actual = productTitleRedSkyService.findEntity(1);

        // Assertions
        assertThat(actual).isNotEmpty();
        assertThat(actual.get()).isEqualTo(redSkyResponse);
    }
}