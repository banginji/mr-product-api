package com.myretail.mrproductapi.domain.redsky;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record RedSkyProductItem(@JsonProperty("tcin") String tcin, @JsonProperty("product_description") RedSkyProductItemDesc productDescription) {
}
