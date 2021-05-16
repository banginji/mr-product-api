package com.myretail.mrproductapi.config;

import com.myretail.mrproductapi.domain.ProductInfo;
import com.myretail.mrproductapi.service.ProductInfoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RouterFunctions.route;
import static org.springframework.web.servlet.function.ServerResponse.ok;

@Configuration
public class Routes {
    @Bean
    public RouterFunction<ServerResponse> productRoutes(ProductInfoService service) {
        return route()
                .GET(
                        "/product/{id}",
                        req -> {
                            Integer id = Integer.parseInt(req.pathVariable("id"));
                            return ok().body(
                                    new ProductInfo(
                                            id,
                                            service.getTitle(id),
                                            service.getPrice(id)
                                    )
                            );
                        }
                )
                .build();
    }
}
