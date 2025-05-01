package com.felix.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayserverApplication {

  public static void main(String[] args) {
    SpringApplication.run(GatewayserverApplication.class, args);
  }

  @Bean
  RouteLocator miniBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
    return routeLocatorBuilder.routes()
      .route(p -> p.
        path("/minibank/accounts/**")
        .filters(f -> f.rewritePath("/minibank/accounts/(?<segment>.*)", "/${segment}"))
        .uri("lb://ACCOUNTS"))
      .route(p -> p.
        path("/minibank/cards/**")
        .filters(f -> f.rewritePath("/minibank/cards/(?<segment>.*)", "/${segment}"))
        .uri("lb://CARDS"))
      .route(p -> p.
        path("/minibank/loans/**")
        .filters(f -> f.rewritePath("/minibank/loans/(?<segment>.*)", "/${segment}"))
        .uri("lb://LOANS"))
      .build();

  }

}
