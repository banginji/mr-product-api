package com.myretail.mrproductapi.converter;

import com.myretail.mrproductapi.domain.redsky.RedSkyResponse;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RedSkyResponseConverter implements ProductTitleResponseConverter<RedSkyResponse> {
    @Override
    public Optional<String> convert(Optional<RedSkyResponse> source) {
        return source.map(response -> response.product().item().productDescription().title());
    }
}
