package com.myretail.mrproductapi.service;

import com.myretail.mrproductapi.converter.PriceResponseConverter;
import com.myretail.mrproductapi.domain.ProductPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductPriceServiceImpl implements ProductPriceService {
    private final DataStoreService dataStoreService;
    private final PriceResponseConverter priceResponseConverter;

    @Override
    public Optional<ProductPrice> getPrice(Integer id) {
        return priceResponseConverter.convert(dataStoreService.getPrice(id));
    }
}
