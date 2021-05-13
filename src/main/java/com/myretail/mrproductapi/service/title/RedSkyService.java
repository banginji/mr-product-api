package com.myretail.mrproductapi.service.title;

import com.myretail.mrproductapi.domain.redsky.RedSkyResponse;

import java.util.Optional;

public interface RedSkyService {
    Optional<RedSkyResponse> findTitleData(Integer id);
}
