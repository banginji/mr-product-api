package com.myretail.mrproductapi.domain.price;

import java.util.Optional;

public record UpdatePriceInfo(Integer id, Optional<Double> value, Optional<String> currencyCode) {
}
