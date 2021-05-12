package com.myretail.mrproductapi.service;

import java.util.Optional;

public interface ProductTitleService {
    Optional<String> getTitle(Integer id);
}
