package com.myretail.mrproductapi.service.title;

import com.myretail.mrproductapi.domain.redsky.RedSkyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedSkyTitleFetcherService extends ProductTitleFetcherService<RedSkyResponse, Integer> {
    private final RedSkyService redSkyService;

    @Override
    public Optional<RedSkyResponse> findEntity(Integer id) {
        return redSkyService.findTitleData(id);
    }

    @Override
    public Optional<String> convert(Optional<RedSkyResponse> source) {
        return source.map(titleData -> titleData.product().item().productDescription().title());
    }
}
