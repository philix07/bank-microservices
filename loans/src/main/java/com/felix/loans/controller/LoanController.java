package com.felix.loans.controller;

import com.felix.loans.dto.LoanRequestDTO;
import com.felix.loans.dto.LoanResponseDTO;
import com.felix.loans.dto.ResponseDTO;
import com.felix.loans.exception.ErrorResponse;
import com.felix.loans.service.ILoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
  name = "CRUD REST APIs for Loan in MyBank",
  description = "CREATE, READ, UPDATE and DELETE 'loan' details"
)
@AllArgsConstructor
@RestController
@RequestMapping(path = "v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoanController {

  private ILoanService loanService;

  @Operation(
    summary = "Get Loan by Mobile Number REST API",
    description = "REST API to fetch Loan data by using customer's mobile number"
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "HTTP Status OK"
    ),
    @ApiResponse(
      responseCode = "404",
      description = "HTTP Status Not_Found",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class)
      )
    )
  })
  @GetMapping("loans")
  public ResponseEntity<LoanResponseDTO> getLoanByMobileNumber(@RequestParam String mobileNumber) {
    return ResponseEntity.ok(loanService.getLoanByMobileNumber(mobileNumber));
  }

  @Operation(
    summary = "Get Loan by Loan ID REST API",
    description = "REST API to fetch Loan data by using loan id"
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "HTTP Status OK"
    ),
    @ApiResponse(
      responseCode = "404",
      description = "HTTP Status Not_Found",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class)
      )
    )
  })
  @GetMapping("loans/{id}")
  public ResponseEntity<LoanResponseDTO> getLoanById(@PathVariable Long id) {
    return ResponseEntity.ok(loanService.getLoanById(id));
  }

  @Operation(
    summary = "Create new Loan REST API",
    description = "REST API to create new Loan inside MyBank"
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "HTTP Status OK"
    ),
    @ApiResponse(
      responseCode = "409",
      description = "HTTP Status Conflict",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class)
      )
    )
  })
  @PostMapping("loans")
  public ResponseEntity<ResponseDTO> createNewLoan(@Valid @RequestBody LoanRequestDTO loanRequestDTO) {
    loanService.createLoan(loanRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED)
      .body(new ResponseDTO(
        201,
        "New loan resource successfully created"
      ));
  }

  @Operation(
    summary = "Update Loan by id REST API",
    description = "REST API to create new Loan data inside MyBank"
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "HTTP Status OK"
    ),
    @ApiResponse(
      responseCode = "404",
      description = "HTTP Status Not_Found",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class)
      )
    ),
    @ApiResponse(
      responseCode = "409",
      description = "HTTP Status Conflict",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class)
      )
    )
  })
  @PatchMapping("loans/{id}")
  public ResponseEntity<ResponseDTO> updateLoanById(@PathVariable Long id, @Valid @RequestBody LoanRequestDTO loanRequestDTO) {
    loanService.updateLoanById(id, loanRequestDTO);
    return ResponseEntity.ok(new ResponseDTO(
      200,
      "Loan data with id : " + id + " successfully updated"
    ));
  }

  @Operation(
    summary = "Delete Loan by id REST API",
    description = "REST API to create new loan inside MyBank"
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "HTTP Status OK"
    ),
    @ApiResponse(
      responseCode = "404",
      description = "HTTP Status Not_Found",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class)
      )
    )
  })
  @DeleteMapping("loans/{id}")
  public ResponseEntity<ResponseDTO> deleteLoanById(@PathVariable Long id) {
    loanService.deleteLoanById(id);
    return ResponseEntity.ok(new ResponseDTO(
      200,
      "Loan data with id : " + id + " successfully deleted"
    ));
  }
}
