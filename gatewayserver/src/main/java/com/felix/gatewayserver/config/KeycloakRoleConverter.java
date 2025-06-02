package com.felix.gatewayserver.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

  @Override
  public Collection<GrantedAuthority> convert(Jwt source) {
    // For more details about the payload format, convert the JWT token in 'https://jwt.io/'
    // The 'getClaims()' method lets you access the payload (claims) inside the access token.
    // 'get("realm_access")' retrieves the data associated with the key "realm_access" in the payload.
    // The "realm_access" section contains all the roles assigned to the current client.
    Map<String, Object> realmAccess = (Map<String, Object>) source.getClaims().get("realm_access");
    if (realmAccess == null || realmAccess.isEmpty()) {
      return new ArrayList<>();
    }

    Collection<GrantedAuthority> returnValue = ((List<String>) realmAccess.get("roles"))
      // We require the "ROLE_" prefix because Spring Security is using that format
      .stream().map(roleName -> "ROLE_" + roleName)
      .map(SimpleGrantedAuthority::new)
      .collect(Collectors.toList());
    return returnValue;

  }

}
