package com.myretail.mrproductapi.service.title;

import com.myretail.mrproductapi.converter.ProductTitleResponseConverter;
import com.myretail.mrproductapi.domain.redsky.RedSkyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductTitleService extends AbstractProductTitleService<RedSkyResponse> {
    private final ProductTitleResponseConverter<RedSkyResponse> productTitleResponseConverter;
    private final RedSkyServiceImpl redSkyService;

    @Override
    public ProductTitleResponseConverter<RedSkyResponse> getConverter() {
        return productTitleResponseConverter;
    }

    @Override
    public Optional<RedSkyResponse> findEntity(Integer id) {
        return redSkyService.findTitleData(id);
    }
}
