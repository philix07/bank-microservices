package com.felix.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
  name = "Loan Request Entity",
  description = "Schema to handle creating and updating account information"
)
public class LoanRequestDTO {

  @NotEmpty(message = "Mobile Number can not be a null or empty")
  @Pattern(regexp = "^0[0-9]{9,14}$", message = "Mobile number must start with 0 and be 10-15 digits long")
  @Schema(
    description = "Mobile Number of Customer", example = "4365327698"
  )
  private String mobileNumber;


  @NotEmpty(message = "LoanType can not be a null or empty")
  @Schema(
    description = "Type of the loan", example = "Home Loan"
  )
  private String loanType;

  @Positive(message = "Total loan amount should be greater than zero")
  @Schema(
    description = "Total loan amount", example = "100000"
  )
  private int totalLoan;

  @PositiveOrZero(message = "Total loan amount paid should be equal or greater than zero")
  @Schema(
    description = "Total loan amount paid", example = "1000"
  )
  private int amountPaid;

  @PositiveOrZero(message = "Total outstanding amount should be equal or greater than zero")
  @Schema(
    description = "Total outstanding amount against a loan", example = "99000"
  )
  private int outstandingAmount;

}
