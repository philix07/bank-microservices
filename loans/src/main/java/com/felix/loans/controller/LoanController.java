package com.felix.loans.controller;

import com.felix.loans.dto.LoanRequestDTO;
import com.felix.loans.dto.LoanResponseDTO;
import com.felix.loans.dto.ResponseDTO;
import com.felix.loans.service.ILoanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
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
@Validated
@RestController
@RequestMapping(path = "v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoanController {

  private ILoanService loanService;


  @GetMapping("loans")
  public ResponseEntity<LoanResponseDTO> getLoanByMobileNumber(@RequestParam String mobileNumber) {
    return ResponseEntity.ok(loanService.getLoanByMobileNumber(mobileNumber));
  }

  @GetMapping("loans/{id}")
  public ResponseEntity<LoanResponseDTO> getLoanById(@PathVariable Long id) {
    return ResponseEntity.ok(loanService.getLoanById(id));
  }

  @PostMapping("loans")
  public ResponseEntity<ResponseDTO> createNewLoan(@RequestBody LoanRequestDTO loanRequestDTO) {
    loanService.createLoan(loanRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED)
      .body(new ResponseDTO(
        201,
        "New loan resource successfully created"
      ));
  }

  @PatchMapping("loans/{id}")
  public ResponseEntity<ResponseDTO> updateLoanById(@PathVariable Long id, @RequestBody LoanRequestDTO loanRequestDTO) {
    loanService.updateLoan(id, loanRequestDTO);
    return ResponseEntity.ok(new ResponseDTO(
      200,
      "Loan data with id : " + id + " successfully updated"
    ));
  }

  @DeleteMapping("loans/{id}")
  public ResponseEntity<ResponseDTO> deleteLoanById(@PathVariable Long id) {
    loanService.deleteLoan(id);
    return ResponseEntity.ok(new ResponseDTO(
      200,
      "Loan data with id : " + id + " successfully deleted"
    ));
  }
}
