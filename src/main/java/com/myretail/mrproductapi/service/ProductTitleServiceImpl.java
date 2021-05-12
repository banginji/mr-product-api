package com.myretail.mrproductapi.service;

import com.myretail.mrproductapi.converter.RedSkyResponseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductTitleServiceImpl implements ProductTitleService {
    private final RedSkyService redSkyService;
    private final RedSkyResponseConverter redSkyResponseConverter;

    @Override
    public Optional<String> getTitle(Integer id) {
        return redSkyResponseConverter.convert(redSkyService.getTitle(id));
    }
}
