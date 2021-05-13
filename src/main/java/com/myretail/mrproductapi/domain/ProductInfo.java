package com.myretail.mrproductapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductInfo(@JsonProperty("id") Integer id, @JsonProperty("title") Optional<String> title, @JsonProperty("price") Optional<ProductPrice> price) {
    public static ProductInfo of(Integer id) {
        return new ProductInfo(id, Optional.empty(), Optional.empty());
    }
}
