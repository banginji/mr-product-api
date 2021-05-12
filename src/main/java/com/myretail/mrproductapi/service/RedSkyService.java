package com.myretail.mrproductapi.service;

import com.myretail.mrproductapi.domain.redsky.RedSkyResponse;

import java.util.Optional;

public interface RedSkyService {
    public Optional<RedSkyResponse> getTitle(Integer id);
}
