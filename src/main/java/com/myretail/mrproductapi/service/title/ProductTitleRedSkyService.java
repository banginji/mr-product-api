package com.myretail.mrproductapi.service.title;

import com.myretail.mrproductapi.converter.ProductTitleResponseConverter;
import com.myretail.mrproductapi.domain.redsky.RedSkyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductTitleRedSkyService extends ProductTitleService<RedSkyResponse> {
    private final RedSkyService redSkyService;

    @Override
    public ProductTitleResponseConverter<RedSkyResponse> getConverter() {
        return source -> source.map(response -> response.product().item().productDescription().title());
    }

    @Override
    public Optional<RedSkyResponse> findEntity(Integer id) {
        return redSkyService.findTitleData(id);
    }
}
