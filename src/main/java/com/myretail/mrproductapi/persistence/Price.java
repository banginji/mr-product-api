package com.myretail.mrproductapi.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public record Price(@Id Integer id, Double value, String currencyCode) {
}
