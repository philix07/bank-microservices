package com.felix.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(
  name = "Card Request DTO",
  description = "Schema to handle creating and updating card information"
)
@AllArgsConstructor
@Data
public class CardRequestDTO {

  @Schema(
    description = "Customer's mobile number of MyBank account",
    example = "085312341234"
  )
  @NotEmpty(message = "mobileNumber must be filled")
  @Pattern(regexp = "^0[0-9]{9,14}$", message = "Mobile number must start with 0 and be 10-15 digits long")
  private String mobileNumber;

  @Schema(
    description = "Type of the card", example = "Credit Card"
  )
  @NotEmpty(message = "CardType can not be a null or empty")
  private String cardType;

  @Schema(
    description = "Total amount limit available against a card", example = "100000"
  )
  @Positive(message = "Total card limit should be greater than zero")
  private int totalLimit;

  @Schema(
    description = "Total amount used by a Customer", example = "1000"
  )
  @PositiveOrZero(message = "Total amount used should be equal or greater than zero")
  private int amountUsed;

  @Schema(
    description = "Total available amount against a card", example = "90000"
  )
  @PositiveOrZero(message = "Total available amount should be equal or greater than zero")
  private int availableAmount;


}
