package com.myretail.mrproductapi.service;

import java.util.Optional;

public abstract class EntityUpdateService<IN, KEY> implements ProductInfoFetcherService<Optional<IN>, KEY>, ProductInfoUpdateService<IN> {
    public void updateEntityIfFound(KEY id) {
        this.findEntity(id).ifPresent(this::updateEntity);
    }
}
