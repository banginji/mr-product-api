package com.myretail.mrproductapi.service;

import com.myretail.mrproductapi.persistence.Price;

import java.util.Optional;

public interface DataStoreService {
    public Optional<Price> getPrice(Integer id);
}
