package com.myretail.mrproductapi.service.price;

import com.myretail.mrproductapi.domain.price.UpdatePriceInfo;
import com.myretail.mrproductapi.persistence.Price;
import com.myretail.mrproductapi.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataSourceServiceImpl implements DataSourceService {
    private final PriceRepository priceRepository;

    @Override
    public Optional<Price> findById(Integer id) {
        return priceRepository.findById(id);
    }

    @Override
    public Optional<Price> patchUpdate(UpdatePriceInfo updatePriceInfo) {
        return findById(updatePriceInfo.id()).map(
                price ->
                        priceRepository.save(
                                new Price(
                                        price.id(),
                                        updatePriceInfo.value().orElse(price.value()),
                                        updatePriceInfo.currencyCode().orElse(price.currencyCode())
                                )
                        )

        );
    }
}
