package com.myretail.mrproductapi.domain.price;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdatePriceRequest(@JsonProperty("value") Optional<Double> value, @JsonProperty("currencyCode") Optional<String> currencyCode) {
}
