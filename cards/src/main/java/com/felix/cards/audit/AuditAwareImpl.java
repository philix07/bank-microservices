package com.felix.cards.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

// this class is used to automate filling the createdAt,
// createdBy, updatedAt and updatedBy value from BaseEntity
@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {
  @Override
  public Optional<String> getCurrentAuditor() {
    // "ACCOUNTS_MS" refers to account microservices.
    return Optional.of("CARDS_MS");
  }
}
