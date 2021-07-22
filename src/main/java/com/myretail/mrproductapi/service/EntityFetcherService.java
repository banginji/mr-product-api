package com.myretail.mrproductapi.service;

import org.springframework.core.convert.converter.Converter;

import java.util.Optional;

public abstract class EntityFetcherService<IN, OUT, KEY> implements Converter<Optional<IN>, Optional<OUT>>, ProductInfoFetcherService<Optional<IN>, KEY> {
    public Optional<OUT> getEntity(KEY id) {
        return this.convert(this.findEntity(id));
    }
}
