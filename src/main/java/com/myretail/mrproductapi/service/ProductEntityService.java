package com.myretail.mrproductapi.service;

import org.springframework.core.convert.converter.Converter;

import java.util.Optional;

public interface ProductEntityService<T, U> {
    Converter<Optional<T>, Optional<U>> getConverter();
    Optional<T> findEntity(Integer id);

    default Optional<U> getEntity(Integer id) {
        return getConverter().convert(findEntity(id));
    }
}
