package com.myretail.mrproductapi.converter;

import com.myretail.mrproductapi.domain.redsky.RedSkyProduct;
import com.myretail.mrproductapi.domain.redsky.RedSkyProductItem;
import com.myretail.mrproductapi.domain.redsky.RedSkyProductItemDesc;
import com.myretail.mrproductapi.domain.redsky.RedSkyResponse;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class RedSkyResponseConverterTest {
    @Test
    void someTitleConversionTest() {
        String title = "t1";
        RedSkyResponse titleData = new RedSkyResponse(new RedSkyProduct(new RedSkyProductItem("1", new RedSkyProductItemDesc(title))));
        RedSkyResponseConverter converter = new RedSkyResponseConverter();
        Optional<String> result = converter.convert(Optional.of(titleData));

        Optional<String> expected = Optional.of(title);
        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void emptyTitleConversionTest() {
        Optional<RedSkyResponse> titleData = Optional.empty();
        RedSkyResponseConverter converter = new RedSkyResponseConverter();
        Optional<String> result = converter.convert(titleData);

        assertThat(result).isEmpty();
    }
}