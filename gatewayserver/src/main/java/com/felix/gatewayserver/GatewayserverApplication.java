package com.felix.gatewayserver;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

  public static void main(String[] args) {
    SpringApplication.run(GatewayserverApplication.class, args);
  }

  @Bean
  RouteLocator miniBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
    // "lb://ACCOUNTS" -> if multiple instances of "ACCOUNTS" exist, Spring Cloud LoadBalancer
    // chooses one based on a load-balancing algorithm (default: round-robin).
    return routeLocatorBuilder.routes()
      .route(p -> p
        .path("/minibank/accounts/**")
        .filters(f -> f
          .rewritePath("/minibank/accounts/(?<segment>.*)", "/${segment}")
          .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
          .circuitBreaker(circuitBreakerConfig -> circuitBreakerConfig
            .setName("accountsCircuitBreaker")
            .setFallbackUri("forward:/contact-support") // Fallback mechanism. Whenever exception happened, forward to this path
          )
        )
        .uri("lb://ACCOUNTS"))
      .route(p -> p
        .path("/minibank/cards/**")
        .filters(f -> f
          .rewritePath("/minibank/cards/(?<segment>.*)", "/${segment}")
          .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
          .requestRateLimiter(rateLimiterConfig -> rateLimiterConfig
            .setRateLimiter(redisRateLimiter())
            .setKeyResolver(userKeyResolver())
          )
        )
        .uri("lb://CARDS"))
      .route(p -> p
        .path("/minibank/loans/**")
        .filters(f -> f
          .rewritePath("/minibank/loans/(?<segment>.*)", "/${segment}")
          .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
          .retry(retryConfig -> retryConfig
            .setRetries(3)
            .setMethods(HttpMethod.GET)
            .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true)
          )
        )
        .uri("lb://LOANS"))
      .build();
  }

  @Bean
  public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
    return factory -> factory
      .configureDefault(id -> new Resilience4JConfigBuilder(id)
        .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
        .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build())
        .build()
      );
  }

  @Bean
  public RedisRateLimiter redisRateLimiter() {
    return new RedisRateLimiter(1, 1, 1);
  }

  @Bean
  KeyResolver userKeyResolver() {
    return exchange -> Mono
      .justOrEmpty(exchange.getRequest().getHeaders().getFirst("user"))
      .defaultIfEmpty("anonymous");
  }

}
