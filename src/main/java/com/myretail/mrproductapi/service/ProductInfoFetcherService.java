package com.myretail.mrproductapi.service;

@FunctionalInterface
public interface ProductInfoFetcherService<OUT, KEY> {
    OUT findEntity(KEY id);
}
