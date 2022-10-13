package com.samsteel.gatewaydemo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfig {
    private final static Logger logger = LoggerFactory.getLogger(MainConfig.class);

    private final String SMART_VR_SERVICE_NAME = "smart-vr-api";
    private final String SLEUTH_CONSUMER_SERVICE = "sleuth-consumer";
    private final String SLEUTH_PRODUCER_SERVICE = "sleuth-producer";

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/customers/", "/customers")
                        .filters(f -> f.rewritePath("/customers", "/customers/"))
                        .uri(String.format("lb://%s/", SMART_VR_SERVICE_NAME)))
                .route(p -> p
                        .path("/orders/**")
                        .filters(rw -> rw.rewritePath("/orders/(?<orderId>.*)", "/orders/${orderId}"))
                        .uri(String.format("lb://%s/", SMART_VR_SERVICE_NAME)))
                .route(p -> p
                        .path("/sleuth-consumer/**","/sleuth-consumer/","/sleuth-consumer")
                        .filters(rw -> rw.rewritePath("/sleuth-consumer/?(?<variable>.*)", "/${variable}"))
                        .uri(String.format("lb://%s/", SLEUTH_CONSUMER_SERVICE)))
                .build();
    }
}
