package com.myretail.mrproductapi.service;

import java.util.Optional;

@FunctionalInterface
public interface ProductInfoFetcherService<KEY, OUT> {
    Optional<OUT> findEntity(KEY id);
}
