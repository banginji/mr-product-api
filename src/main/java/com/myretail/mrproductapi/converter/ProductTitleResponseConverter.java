package com.myretail.mrproductapi.converter;

import org.springframework.core.convert.converter.Converter;

import java.util.Optional;

public interface ProductTitleResponseConverter<T> extends Converter<Optional<T>, Optional<String>> {
}
