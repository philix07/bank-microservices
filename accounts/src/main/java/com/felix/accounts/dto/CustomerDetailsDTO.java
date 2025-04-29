package com.felix.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
  name = "CustomerDetails",
  description = "Schema to hold customer's Account, Cards and Loans information"
)
public class CustomerDetailsDTO {

  @Schema(
    description = "Account details of the Customer"
  )
  private AccountResponseDTO accountsDto;

  @Schema(
    description = "Loans details of the Customer"
  )
  private LoanResponseDTO loansDto;

  @Schema(
    description = "Cards details of the Customer"
  )
  private CardResponseDTO cardsDto;
}
