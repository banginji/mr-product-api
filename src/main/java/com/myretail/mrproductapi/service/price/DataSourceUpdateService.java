package com.myretail.mrproductapi.service.price;

import com.myretail.mrproductapi.domain.price.UpdatePriceInfo;
import com.myretail.mrproductapi.persistence.Price;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataSourceUpdateService extends ProductPriceUpdateService<Price> {
    private final DataSourceService dataSourceService;

    @Override
    public Optional<Price> findEntity(UpdatePriceInfo id) {
        return dataSourceService.findById(id.id());
    }

    @Override
    public void updateEntity(Price entity) {
        dataSourceService.save(entity);
    }
}
