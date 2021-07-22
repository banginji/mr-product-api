package com.myretail.mrproductapi.service;

@FunctionalInterface
public interface ProductInfoUpdateService<T> {
    void updateEntity(T entity);
}
