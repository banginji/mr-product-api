package com.myretail.mrproductapi.service;

import org.springframework.core.convert.converter.Converter;

import java.util.Optional;

public interface ProductEntityService<IN, OUT, KEY> {
    Converter<Optional<IN>, Optional<OUT>> converter();
    ProductInfoFetcherService<KEY, IN> fetcherService();

    default Optional<OUT> getEntity(KEY id) {
        return converter().convert(fetcherService().findEntity(id));
    }
}
