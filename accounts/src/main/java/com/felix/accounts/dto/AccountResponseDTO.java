package com.felix.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
  name = "Account Response Entity",
  description = "Schema to handle fetching account information"
)
public class AccountResponseDTO {

  @Schema(
    description = "Account number of Customer's MyBank account",
    example = "5379421420"
  )
  private Long accountNumber;

  @Schema(
    description = "Account type of Customer's MyBank account",
    example = "Savings"
  )
  private String accountType;

  @Schema(
    description = "MyBank branch address",
    example = "Pemangkat"
  )
  private String branchAddress;

  @Schema(
    description = "Customer's name of MyBank account",
    example = "Felix"
  )
  private String name;

  @Schema(
    description = "Customer's email of MyBank account",
    example = "felix.liando07@gmail.com"
  )
  private String email;

  @Schema(
    description = "Customer's mobile number of MyBank account",
    example = "085312341234"
  )
  private String mobileNumber;

}
