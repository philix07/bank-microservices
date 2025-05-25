package com.felix.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

  @Bean
  public SecurityWebFilterChain gatewaySecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
//    The pattern below requires every request ('anyExchange()') to be authenticated before being processed
//    serverHttpSecurity.authorizeExchange(exchanges -> exchanges
//      .anyExchange().authenticated()
//    );

    return serverHttpSecurity
      // 'exchanges' refers to incoming HTTP requests.
      // The order of configuration determines priority (top to bottom: 1 > 2 > 3).
      // In the configuration below:
      // - All GET requests are allowed without authentication.
      //   It means you can still fetch minibank/accounts, but you can't do POST or UPDATE or DELETE
      // - Any request to '/minibank/accounts/**', '/minibank/cards/**', or '/minibank/loans/**'
      //   must be authenticated.
      .authorizeExchange(exchanges -> exchanges
        .pathMatchers(HttpMethod.GET).permitAll()
        .pathMatchers("/minibank/accounts/**").authenticated()
        .pathMatchers("/minibank/cards/**").authenticated()
        .pathMatchers("/minibank/loans/**").authenticated()
      )
      .oauth2ResourceServer(resourceServer -> resourceServer
        .jwt(Customizer.withDefaults())
      )
      .csrf(csrfSpec -> csrfSpec.disable())
      .build();

  }

}
