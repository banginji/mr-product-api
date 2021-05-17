package com.myretail.mrproductapi.service;

@FunctionalInterface
public interface ProductInfoUpdateService<IN, OUT> {
    OUT updateEntity(IN updateEntity);
}
