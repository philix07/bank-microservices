package com.felix.accounts.service.client;

import com.felix.accounts.dto.CardResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("cards")
public interface CardsFeignClient {

  @GetMapping(value = "v1/cards", consumes = "application/json")
  public ResponseEntity<CardResponseDTO> getCardByMobileNumber(
    @RequestHeader("minibank-correlation-id") String correlationId,
    @RequestParam String mobileNumber
  );

}
