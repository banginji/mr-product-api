package com.myretail.mrproductapi.service;

import org.springframework.core.convert.converter.Converter;

import java.util.Optional;

public interface ProductEntityService<IN, OUT, KEY> {
    Converter<Optional<IN>, Optional<OUT>> responseConverter();
    ProductInfoFetcherService<Optional<IN>, KEY> fetcherService();

    default Optional<OUT> getEntity(KEY id) {
        return responseConverter().convert(fetcherService().findEntity(id));
    }
}
