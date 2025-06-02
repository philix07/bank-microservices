package com.felix.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

  @Bean
  public SecurityWebFilterChain gatewaySecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
//    The pattern below requires every request ('anyExchange()') to be authenticated before being processed
//    serverHttpSecurity.authorizeExchange(exchanges -> exchanges
//      .anyExchange().authenticated()
//    );

    // 'exchanges' refers to incoming HTTP requests.
    // The order of configuration determines priority (top to bottom: 1 > 2 > 3).
    // In the configuration below:
    // - All GET requests are allowed without authentication.
    //   It means you can still fetch minibank/accounts, but you can't do POST or UPDATE or DELETE
    // - Any request to '/minibank/accounts/**', '/minibank/cards/**', or '/minibank/loans/**'
    //   must be authenticated.
    return serverHttpSecurity
      .authorizeExchange(exchanges -> exchanges
        .pathMatchers(HttpMethod.GET).permitAll()
        .pathMatchers("/minibank/accounts/**").hasRole("ACCOUNTS")
        .pathMatchers("/minibank/cards/**").hasRole("CARDS")
        .pathMatchers("/minibank/loans/**").hasRole("LOANS")
      )
      .oauth2ResourceServer(resourceServer -> resourceServer
        // grantedAuthoritiesExtractor() is a custom converter to extract granted authorities (roles) from a JWT token.
        .jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(grantedAuthoritiesExtractor()))
      )
      .csrf(csrfSpec -> csrfSpec.disable())
      .build();
  }

  // This method creates a custom converter to extract granted authorities (roles) from a JWT token.
  private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {

    // Create a standard JWT to AuthenticationToken converter provided by Spring Security.
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

    // Customize the converter by setting a Keycloak-specific converter that extracts roles from the token.
    // KeycloakRoleConverter is a custom class that transforms Keycloak-specific role claims into Spring Security authorities.
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

    // Wrap the converter with a Reactive adapter since we're using reactive security (WebFlux).
    // This makes it compatible with reactive applications and returns a Mono (asynchronous container).
    return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
  }

}
