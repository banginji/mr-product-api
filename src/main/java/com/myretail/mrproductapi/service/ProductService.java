package com.myretail.mrproductapi.service;

import com.myretail.mrproductapi.domain.ProductInfo;
import com.myretail.mrproductapi.domain.ProductPrice;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import graphql.execution.DataFetcherResult;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@DgsComponent
@RequiredArgsConstructor
public class ProductService {
    private final ProductTitleService productTitleService;
    private final ProductPriceService productPriceService;

    @DgsData(parentType = "Query")
    public DataFetcherResult<ProductInfo> getProductInfo(Integer id) {
        return DataFetcherResult.<ProductInfo>newResult()
                .data(ProductInfo.of(id))
                .localContext(id)
                .build();
    }

    @DgsData(parentType = "ProductInfo")
    public Optional<ProductPrice> price(DgsDataFetchingEnvironment dfe) {
        return productPriceService.getPrice(dfe.getLocalContext());
    }

    @DgsData(parentType = "ProductInfo")
    public Optional<String> title(DgsDataFetchingEnvironment dfe) {
        return productTitleService.getTitle(dfe.getLocalContext());
    }
}
