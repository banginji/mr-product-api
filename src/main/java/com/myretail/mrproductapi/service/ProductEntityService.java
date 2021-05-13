package com.myretail.mrproductapi.service;

import org.springframework.core.convert.converter.Converter;

import java.util.Optional;

public interface ProductEntityService<IN, OUT> {
    Converter<Optional<IN>, Optional<OUT>> getConverter();
    Optional<IN> findEntity(Integer id);

    default Optional<OUT> getEntity(Integer id) {
        return getConverter().convert(findEntity(id));
    }
}
