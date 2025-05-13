package com.felix.accounts.service.client;

import com.felix.accounts.dto.LoanResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("loans")
public interface LoansFeignClient {

  @GetMapping(value = "v1/loans", consumes = "application/json")
  public ResponseEntity<LoanResponseDTO> getLoanByMobileNumber(
    @RequestHeader("minibank-correlation-id") String correlationId,
    @RequestParam String mobileNumber
  );

}
