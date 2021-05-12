package com.myretail.mrproductapi.service;

import com.myretail.mrproductapi.persistence.Price;
import com.myretail.mrproductapi.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataStoreServiceImpl implements DataStoreService {
    private final PriceRepository priceRepository;

    @Override
    public Optional<Price> getPrice(Integer id) {
        return priceRepository.findById(id);
    }
}
