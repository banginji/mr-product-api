package com.myretail.mrproductapi.service.price;

import com.myretail.mrproductapi.converter.ProductPriceResponseConverter;
import com.myretail.mrproductapi.domain.ProductPrice;
import com.myretail.mrproductapi.persistence.Price;
import com.myretail.mrproductapi.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataStoreService extends ProductPriceService<Price> {
    private final PriceRepository priceRepository;

    @Override
    public ProductPriceResponseConverter<Price> converter() {
        return source -> source.map(price -> new ProductPrice(price.value(), price.currencyCode()));
    }

    @Override
    public Optional<Price> findEntity(Integer id) {
        return priceRepository.findById(id);
    }
}
