package com.myretail.mrproductapi.service.title;

import com.myretail.mrproductapi.domain.redsky.RedSkyProduct;
import com.myretail.mrproductapi.domain.redsky.RedSkyProductItem;
import com.myretail.mrproductapi.domain.redsky.RedSkyProductItemDesc;
import com.myretail.mrproductapi.domain.redsky.RedSkyResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class RedSkyServiceTest {
    RestTemplate restTemplate;
    @BeforeEach
    void beforeEach() {
        restTemplate = Mockito.mock(RestTemplate.class);
    }

    @Test
    void testWhenRedSkyDoesNotHaveTitleDataForGivenId() {
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any())).thenReturn(null);

        RedSkyService redSkyService = new RedSkyServiceImpl(restTemplate);

        Optional<RedSkyResponse> actual = redSkyService.findTitleData(1);

        // Assertions
        assertThat(actual).isEmpty();
    }

    @Test
    void testWhenRedSkyHasTitleDataForGivenId() {
        String title = "t1";
        RedSkyResponse redSkyResponse = new RedSkyResponse(new RedSkyProduct(new RedSkyProductItem("1", new RedSkyProductItemDesc(title))));
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any())).thenReturn(redSkyResponse);

        RedSkyService redSkyService = new RedSkyServiceImpl(restTemplate);

        Optional<RedSkyResponse> actual = redSkyService.findTitleData(1);

        // Assertions
        assertThat(actual).isNotEmpty();
        assertThat(actual.get()).isEqualTo(redSkyResponse);
    }
}