package com.myretail.mrproductapi;

import com.myretail.mrproductapi.config.Routes;
import com.myretail.mrproductapi.fetcher.ProductInfoFetcher;
import com.myretail.mrproductapi.service.ProductInfoServiceImpl;
import com.myretail.mrproductapi.service.price.DataStoreService;
import com.myretail.mrproductapi.service.title.ProductTitleRedSkyService;
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootTest(classes = {Routes.class, DgsAutoConfiguration.class, ProductInfoFetcher.class, ProductInfoServiceImpl.class, DataStoreService.class, ProductTitleRedSkyService.class})
public abstract class BaseIT {
}