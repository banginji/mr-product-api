package com.myretail.mrproductapi.service;

import com.myretail.mrproductapi.converter.PriceResponseConverter;
import com.myretail.mrproductapi.converter.RedSkyResponseConverter;
import com.myretail.mrproductapi.domain.ProductPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductInfoServiceImpl implements ProductInfoService {
    private final DataStoreService dataStoreService;
    private final PriceResponseConverter priceResponseConverter;

    @Override
    public Optional<ProductPrice> getPrice(Integer id) {
        return priceResponseConverter.convert(dataStoreService.getPrice(id));
    }

    private final RedSkyService redSkyService;
    private final RedSkyResponseConverter redSkyResponseConverter;

    @Override
    public Optional<String> getTitle(Integer id) {
        return redSkyResponseConverter.convert(redSkyService.getTitle(id));
    }
}
