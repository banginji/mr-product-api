package com.myretail.mrproductapi.converter;

import com.myretail.mrproductapi.domain.redsky.RedSkyResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RedSkyResponseConverter implements Converter<Optional<RedSkyResponse>, Optional<String>> {
    @Override
    public Optional<String> convert(Optional<RedSkyResponse> source) {
        return source.map(response -> response.product().item().productDescription().title());
    }
}
