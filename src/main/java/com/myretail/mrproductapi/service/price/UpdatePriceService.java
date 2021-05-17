package com.myretail.mrproductapi.service.price;

import com.myretail.mrproductapi.domain.ProductPrice;
import com.myretail.mrproductapi.domain.price.UpdatePriceInfo;
import com.myretail.mrproductapi.persistence.Price;
import com.myretail.mrproductapi.repository.PriceRepository;
import com.myretail.mrproductapi.service.ProductInfoFetcherService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdatePriceService extends ProductPriceService<Price, UpdatePriceInfo> {
    private final PriceRepository priceRepository;

    @Override
    public Converter<Optional<Price>, Optional<ProductPrice>> converter() {
        return source -> source.map(price -> new ProductPrice(price.value(), price.currencyCode()));
    }

    @Override
    public ProductInfoFetcherService<Optional<Price>, UpdatePriceInfo> fetcherService() {
        return updatePrice -> priceRepository.findById(updatePrice.id())
                .map(price -> priceRepository.save(Objects.requireNonNull(updateConverter().convert(updatePrice))));
    }

    private Converter<UpdatePriceInfo, Price> updateConverter() {
        return updatePrice -> new Price(updatePrice.id(), updatePrice.value(), updatePrice.currencyCode());
    }
}
