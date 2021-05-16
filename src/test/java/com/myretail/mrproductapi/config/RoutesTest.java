package com.myretail.mrproductapi.config;

import com.myretail.mrproductapi.BaseIT;
import com.myretail.mrproductapi.domain.redsky.RedSkyProduct;
import com.myretail.mrproductapi.domain.redsky.RedSkyProductItem;
import com.myretail.mrproductapi.domain.redsky.RedSkyProductItemDesc;
import com.myretail.mrproductapi.domain.redsky.RedSkyResponse;
import com.myretail.mrproductapi.persistence.Price;
import com.myretail.mrproductapi.repository.PriceRepository;
import com.myretail.mrproductapi.service.title.RedSkyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

class RoutesTest extends BaseIT {
    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    RedSkyService redSkyService;

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
        Mockito.when(redSkyService.findTitleData(Mockito.anyInt())).thenReturn(Optional.empty());
        Mockito.when(priceRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        mockMvc
                .perform(MockMvcRequestBuilders.get("/product/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").doesNotExist());

        // Interaction verifications
        Mockito.verify(redSkyService).findTitleData(Mockito.anyInt());
        Mockito.verify(priceRepository).findById(Mockito.anyInt());
    }

    @Test
    void testWhenNoPriceIsAvailableInDataSource() throws Exception {
        String title = "t1";
        Mockito.when(redSkyService.findTitleData(Mockito.anyInt())).thenReturn(Optional.of(new RedSkyResponse(new RedSkyProduct(new RedSkyProductItem("1", new RedSkyProductItemDesc(title))))));
        Mockito.when(priceRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        mockMvc
                .perform(MockMvcRequestBuilders.get("/product/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(title));

        // Interaction verifications
        Mockito.verify(redSkyService).findTitleData(Mockito.anyInt());
        Mockito.verify(priceRepository).findById(Mockito.anyInt());
    }

    @Test
    void testWhenNoTitleDataIsPresentInRedSkyForGivenId() throws Exception {
        Price price = new Price(1, 8.1, "USD");
        Mockito.when(redSkyService.findTitleData(Mockito.anyInt())).thenReturn(Optional.empty());
        Mockito.when(priceRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(price));

        mockMvc
                .perform(MockMvcRequestBuilders.get("/product/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price.value").value(price.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price.currencyCode").value(price.currencyCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").doesNotExist());

        // Interaction verifications
        Mockito.verify(priceRepository).findById(Mockito.anyInt());
        Mockito.verify(redSkyService).findTitleData(Mockito.anyInt());
    }
}