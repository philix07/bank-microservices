package com.felix.accounts.controller;

import com.felix.accounts.dto.CustomerDetailsDTO;
import com.felix.accounts.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
  name = "REST APIs for Customers in MyBank",
  description = "REST API in MyBank to fetch customer details"
)
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(path = "v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

  private final ICustomerService customerService;

  @Operation(
    summary = "Get Customer Details Data REST API",
    description = "REST API to get Customer Details by using Customer's Mobile Number"
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "HttpStatus : Ok"
    ),
    @ApiResponse(
      responseCode = "500",
      description = "HttpStatus : Internal Server Error"
    )
  })
  @GetMapping("customer-details/{mobileNumber}")
  public ResponseEntity<CustomerDetailsDTO> fetchCustomerDetails(@PathVariable String mobileNumber) {
    return ResponseEntity.ok(customerService.fetchCustomerDetails(mobileNumber));
  }

}
