package com.felix.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
  name = "Account Request Entity",
  description = "Schema to handle creating and updating account information"
)
public class AccountRequestDTO {

  @Schema(
    description = "Account type of Customer's MyBank account",
    example = "Savings"
  )
  @NotEmpty(message = "accountType must be filled")
  private String accountType;


  @Schema(
    description = "MyBank branch address",
    example = "Pemangkat"
  )
  @NotEmpty(message = "branchAddress must be filled")
  private String branchAddress;


  @Schema(
    description = "Customer's name of MyBank account",
    example = "Felix"
  )
  @NotEmpty(message = "name must be filled")
  @Size(min = 3, message = "name must be at least 3 character long")
  private String name;


  @Schema(
    description = "Customer's email of MyBank account",
    example = "felix.liando07@gmail.com"
  )
  @NotEmpty(message = "email must be filled")
  @Email(message = "Email must be in valid format")
  private String email;


  @Schema(
    description = "Customer's mobile number of MyBank account",
    example = "085312341234"
  )
  @NotEmpty(message = "mobileNumber must be filled")
  @Pattern(regexp = "^0[0-9]{9,14}$", message = "Mobile number must start with 0 and be 10-15 digits long")
  private String mobileNumber;

}
