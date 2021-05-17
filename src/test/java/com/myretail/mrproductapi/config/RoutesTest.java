package com.myretail.mrproductapi.config;

import com.myretail.mrproductapi.BaseIT;
import com.myretail.mrproductapi.domain.redsky.RedSkyProduct;
import com.myretail.mrproductapi.domain.redsky.RedSkyProductItem;
import com.myretail.mrproductapi.domain.redsky.RedSkyProductItemDesc;
import com.myretail.mrproductapi.domain.redsky.RedSkyResponse;
import com.myretail.mrproductapi.persistence.Price;
import com.myretail.mrproductapi.repository.PriceRepository;
import com.myretail.mrproductapi.service.title.RedSkyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RoutesTest extends BaseIT {
    @Autowired
    WebApplicationContext webApplicationContext;

    @SpyBean
    RedSkyServiceImpl redSkyService;

    @MockBean
    RestTemplate restTemplate;

    @MockBean
    PriceRepository priceRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    void testWhenNoValuesFromBothSourcesForTitleAndPrice() throws Exception {
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any())).thenReturn(null);
        Mockito.when(priceRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        mockMvc
                .perform(get("/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.price").doesNotExist())
                .andExpect(jsonPath("$.title").doesNotExist());

        // Interaction verifications
        Mockito.verify(redSkyService).findTitleData(Mockito.anyInt());
        Mockito.verify(priceRepository).findById(Mockito.anyInt());
    }

    @Test
    void testWhenNoPriceIsAvailableInDataSource() throws Exception {
        String title = "t1";
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any())).thenReturn(new RedSkyResponse(new RedSkyProduct(new RedSkyProductItem("1", new RedSkyProductItemDesc(title)))));
        Mockito.when(priceRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        mockMvc
                .perform(get("/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.price").doesNotExist())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.title").value(title));

        // Interaction verifications
        Mockito.verify(redSkyService).findTitleData(Mockito.anyInt());
        Mockito.verify(priceRepository).findById(Mockito.anyInt());
    }

    @Test
    void testWhenNoTitleDataIsPresentInRedSkyForGivenId() throws Exception {
        Price price = new Price(1, 8.1, "USD");
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any())).thenReturn(null);
        Mockito.when(priceRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(price));

        mockMvc
                .perform(get("/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.price").exists())
                .andExpect(jsonPath("$.price.value").value(price.value()))
                .andExpect(jsonPath("$.price.currencyCode").value(price.currencyCode()))
                .andExpect(jsonPath("$.title").doesNotExist());

        // Interaction verifications
        Mockito.verify(priceRepository).findById(Mockito.anyInt());
        Mockito.verify(redSkyService).findTitleData(Mockito.anyInt());
    }

    @Test
    void testUpdatePriceWhenNoPriceIsFound() throws Exception {
        Mockito.when(priceRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        mockMvc
                .perform(
                        put("/product/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"value\": 2.2, \"currencyCode\": \"USD\"}")
                )
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());

        // Interaction verifications
        Mockito.verify(priceRepository).findById(Mockito.anyInt());
        Mockito.verify(priceRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testUpdatePriceWhenPriceIsFound() throws Exception {
        Price price = new Price(1, 8.1, "USD");
        Mockito.when(priceRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(price));
        Mockito.when(priceRepository.save(Mockito.any())).thenReturn(Mockito.any(Price.class));

        mockMvc
                .perform(
                        put("/product/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"value\": 2.2, \"currencyCode\": \"USD\"}")
                )
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());

        // Interaction verifications
        Mockito.verify(priceRepository).findById(Mockito.anyInt());
        Mockito.verify(priceRepository).save(Mockito.any());
    }

    @Test
    void testWhenHttpClientErrorExceptionIsThrown() throws Exception {
        Mockito.when(redSkyService.findTitleData(Mockito.anyInt())).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
        Mockito.when(priceRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        mockMvc
                .perform(get("/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.price").doesNotExist())
                .andExpect(jsonPath("$.title").doesNotExist());

        // Interaction verifications
        Mockito.verify(priceRepository).findById(Mockito.anyInt());
        Mockito.verify(redSkyService, Mockito.times(2)).findTitleData(Mockito.anyInt());
    }

    @Test
    void testWhenRemoteAccessExceptionIsThrown() throws Exception {
        Mockito.when(redSkyService.findTitleData(Mockito.anyInt())).thenThrow(new RemoteAccessException("remote access error"));
        Mockito.when(priceRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        mockMvc
                .perform(get("/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.price").doesNotExist())
                .andExpect(jsonPath("$.title").doesNotExist());

        // Interaction verifications
        Mockito.verify(priceRepository).findById(Mockito.anyInt());
        Mockito.verify(redSkyService, Mockito.times(2)).findTitleData(Mockito.anyInt());
    }

    @Test
    void testWhenResourceAccessExceptionIsThrown() throws Exception {
        Mockito.when(redSkyService.findTitleData(Mockito.anyInt())).thenThrow(new ResourceAccessException("remote access error"));
        Mockito.when(priceRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        mockMvc
                .perform(get("/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.price").doesNotExist())
                .andExpect(jsonPath("$.title").doesNotExist());

        // Interaction verifications
        Mockito.verify(priceRepository).findById(Mockito.anyInt());
        Mockito.verify(redSkyService, Mockito.times(2)).findTitleData(Mockito.anyInt());
    }
}