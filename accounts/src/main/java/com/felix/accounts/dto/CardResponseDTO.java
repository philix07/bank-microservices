package com.felix.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(
  name = "Card Response DTO",
  description = "Schema to handle fetching card information"
)
@Data
@AllArgsConstructor
public class CardResponseDTO {

  @Schema(
    description = "Customer's mobile number of MyBank account",
    example = "085312341234"
  )
  private String mobileNumber;

  @Schema(
    description = "Card Number of the customer", example = "100646930341"
  )
  private String cardNumber;

  @Schema(
    description = "Type of the card", example = "Credit Card"
  )
  private String cardType;

  @Schema(
    description = "Total amount limit available against a card", example = "100000"
  )
  private int totalLimit;

  @Schema(
    description = "Total amount used by a Customer", example = "1000"
  )
  private int amountUsed;

  @Schema(
    description = "Total available amount against a card", example = "90000"
  )
  private int availableAmount;

}
