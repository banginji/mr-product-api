package com.myretail.mrproductapi.fetcher;

import com.myretail.mrproductapi.BaseIT;
import com.myretail.mrproductapi.domain.redsky.RedSkyProduct;
import com.myretail.mrproductapi.domain.redsky.RedSkyProductItem;
import com.myretail.mrproductapi.domain.redsky.RedSkyProductItemDesc;
import com.myretail.mrproductapi.domain.redsky.RedSkyResponse;
import com.myretail.mrproductapi.persistence.Price;
import com.myretail.mrproductapi.repository.PriceRepository;
import com.myretail.mrproductapi.service.title.RedSkyService;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.LinkedHashMap;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("unchecked")
public class ProductInfoFetcherTest extends BaseIT {
    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @MockBean
    RedSkyService redSkyService;

    @MockBean
    PriceRepository priceRepository;

    @Test
    void testWhenNoValuesFromBothSourcesForTitleAndPrice() {
        Mockito.when(redSkyService.findTitleData(Mockito.anyInt())).thenReturn(Optional.empty());
        Mockito.when(priceRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        int id = 1;
        LinkedHashMap<String, Object> result = dgsQueryExecutor.executeAndExtractJsonPath(
                "{ getProductInfo(id: " + id + ") { id title price { value } } }",
                "data.getProductInfo"
        );

        // Assertions
        assertThat(result.get("id")).isEqualTo(id);
        assertThat(result.get("title")).isNull();
        assertThat(result.get("price")).isNull();

        // Interaction verifications
        Mockito.verify(redSkyService).findTitleData(Mockito.anyInt());
        Mockito.verify(priceRepository).findById(Mockito.anyInt());
    }

    @Test
    void testWhenNoPriceIsAvailableInDataSource() {
        String title = "t1";
        Mockito.when(redSkyService.findTitleData(Mockito.anyInt())).thenReturn(Optional.of(new RedSkyResponse(new RedSkyProduct(new RedSkyProductItem("1", new RedSkyProductItemDesc(title))))));
        Mockito.when(priceRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        LinkedHashMap<String, Object> result = dgsQueryExecutor.executeAndExtractJsonPath(
                "{ getProductInfo(id: 1) { title price { value } } }",
                "data.getProductInfo"
        );

        // Assertions
        assertThat(result.get("title")).isEqualTo(title);
        assertThat(result.get("price")).isNull();

        // Interaction verifications
        Mockito.verify(redSkyService).findTitleData(Mockito.anyInt());
        Mockito.verify(priceRepository).findById(Mockito.anyInt());
    }

    @Test
    void testWhenNoTitleDataIsPresentInRedSkyForGivenId() {
        Price price = new Price(1, 8.1, "USD");
        Mockito.when(redSkyService.findTitleData(Mockito.anyInt())).thenReturn(Optional.empty());
        Mockito.when(priceRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(price));

        LinkedHashMap<String, ?> result = dgsQueryExecutor.executeAndExtractJsonPath(
                "{ getProductInfo(id: 1) { id title price { value currencyCode } } }",
                "data.getProductInfo"
        );

        // Assertions
        assertThat(result.get("title")).isNull();
        assertThat(result.get("price")).isNotNull();

        LinkedHashMap<String, ?> priceMap = (LinkedHashMap<String, ?>) result.get("price");
        assertThat(priceMap.get("value")).isEqualTo(price.value());
        assertThat(priceMap.get("currencyCode")).isEqualTo(price.currencyCode());

        // Interaction verifications
        Mockito.verify(priceRepository).findById(Mockito.anyInt());
        Mockito.verify(redSkyService).findTitleData(Mockito.anyInt());
    }

    @Test
    void testThatNoNetworkCallWasMadeToRedSkyWhenTitleIsNotAsked() {
        String title = "t1";
        Mockito.when(redSkyService.findTitleData(Mockito.anyInt())).thenReturn(Optional.of(new RedSkyResponse(new RedSkyProduct(new RedSkyProductItem("1", new RedSkyProductItemDesc(title))))));
        Mockito.when(priceRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        LinkedHashMap<String, Object> result = dgsQueryExecutor.executeAndExtractJsonPath(
                "{ getProductInfo(id: 1) { price { value } } }",
                "data.getProductInfo"
        );

        // Assertions
        assertThat(result.get("title")).isNull();

        // Interaction verifications
        Mockito.verifyNoInteractions(redSkyService);
    }

    @Test
    void testThatNoRepositoryCallWasMadeToDataStoreWhenPriceIsNotAsked() {
        Price price = new Price(1, 8.1, "USD");
        Mockito.when(redSkyService.findTitleData(Mockito.anyInt())).thenReturn(Optional.empty());
        Mockito.when(priceRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(price));

        LinkedHashMap<String, Object> result = dgsQueryExecutor.executeAndExtractJsonPath(
                "{ getProductInfo(id: 1) { title } }",
                "data.getProductInfo"
        );

        // Assertions
        assertThat(result.get("price")).isNull();

        // Interaction verifications
        Mockito.verifyNoInteractions(priceRepository);
    }
}
