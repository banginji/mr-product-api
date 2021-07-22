package com.myretail.mrproductapi.service.price;

import com.myretail.mrproductapi.domain.price.UpdatePriceInfo;
import com.myretail.mrproductapi.persistence.Price;

import java.util.Optional;

public interface DataSourceService {
    Optional<Price> findById(Integer id);
    void save(Price price);
    Optional<Price> patchUpdate(UpdatePriceInfo updatePriceInfo);
}
