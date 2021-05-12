package com.myretail.mrproductapi.repository;

import com.myretail.mrproductapi.persistence.Price;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PriceRepository extends MongoRepository<Price, Integer> {
}
