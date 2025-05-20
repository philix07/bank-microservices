package com.felix.accounts.service.client;

import com.felix.accounts.dto.CardResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallback implements CardsFeignClient {

  /**
   * Fallback method for the CardsFeignClient interface.
   * *
   * This method is invoked automatically when the cards microservice is unavailable or fails,
   * as part of a circuit breaker or fallback mechanism (e.g., Resilience4j, Hystrix).
   * *
   * In this implementation, it returns null to gracefully degrade the service and prevent
   * the overall request from failing. This allows the calling service to continue functioning
   * and return partial data (e.g., from accounts or loans microservices).
   * *
   * In a real-world application, you could return cached data, a default response, or a meaningful error message
   * depending on business requirements.
   */
  @Override
  public ResponseEntity<CardResponseDTO> getCardByMobileNumber(String correlationId, String mobileNumber) {
    return null;
  }

}
