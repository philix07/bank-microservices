package com.felix.accounts.controller;

import com.felix.accounts.dto.AccountRequestDTO;
import com.felix.accounts.dto.AccountResponseDTO;
import com.felix.accounts.dto.AccountsContactInfoDTO;
import com.felix.accounts.dto.ResponseDTO;
import com.felix.accounts.exception.general.ErrorResponse;
import com.felix.accounts.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
  name = "CRUD REST APIs for Account in MyBank",
  description = "CREATE, READ, UPDATE and DELETE 'account' details"
)
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(path = "v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

  // key value can be found in application.yml
  @Value("${build.version}")
  private String buildVersion;

  private final IAccountService accountService;

  // environment to get environment variable's configuration
  private final Environment environment;

  // AccountsContactInfoDTO to get configuration value created using @ConfigurationProperties feature
  private final AccountsContactInfoDTO accountsContactInfoDTO;

  @Operation(
    summary = "Get Account's Data REST API",
    description = "REST API to get Account details by using Customer's Mobile Number"
  )
  @ApiResponse(
    responseCode = "200",
    description = "HttpStatus : Ok"
  )
  @GetMapping("accounts")
  ResponseEntity<AccountResponseDTO> getByMobileNumber(
    @Pattern(regexp = "^0[0-9]{9,14}$", message = "Mobile number must start with 0 and be 10-15 digits long")
    @RequestParam String mobileNumber
  ) {
    return ResponseEntity.ok(accountService.getAccountByMobileNumber(mobileNumber));
  }


  @Operation(
    summary = "Get Account's Data REST API",
    description = "REST API to get Account details by using Customer's ID"
  )
  @ApiResponse(
    responseCode = "200",
    description = "HttpStatus : Ok"
  )
  @GetMapping("accounts/{id}")
  ResponseEntity<AccountResponseDTO> getAccountByCustomerId(@PathVariable Long id) {
    return ResponseEntity.ok(accountService.getAccountById(id));
  }


  @Operation(
    summary = "Create Account REST API",
    description = "REST API to create new Customer & Account data"
  )
  @ApiResponse(
    responseCode = "201",
    description = "HttpStatus : Created"
  )
  @PostMapping("accounts")
  ResponseEntity<ResponseDTO> createAccount(@Valid @RequestBody AccountRequestDTO accountRequestDTO) {
    accountService.createAccounts(accountRequestDTO);

    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(new ResponseDTO(
        HttpStatus.CREATED.value(),
        "New Account Successfully Created"
      ));
  }

  @Operation(
    summary = "Update Account's Data REST API",
    description = "REST API to update existing Customer & Account data"
  )
  @ApiResponse(
    responseCode = "200",
    description = "HttpStatus : Ok"
  )
  @PatchMapping("accounts/{id}")
  ResponseEntity<ResponseDTO> updateAccountById(@PathVariable Long id, @Valid @RequestBody AccountRequestDTO accountRequestDTO) {
    accountService.updateAccount(id, accountRequestDTO);
    return ResponseEntity.ok(new ResponseDTO(
      HttpStatus.OK.value(),
      "Account with id " + id + " updated successfully"
    ));
  }

  @Operation(
    summary = "Delete Account's Data REST API",
    description = "REST API to update existing Customer & Account data"
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "HttpStatus : Ok"
    ),
    @ApiResponse(
      responseCode = "404",
      description = "HttpStatus : Not_Found",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class)
      )
    )
  })
  @DeleteMapping("accounts/{id}")
  ResponseEntity<ResponseDTO> deleteAccountById(@PathVariable Long id) {
    boolean isDeleted = accountService.deleteAccount(id);

    if (isDeleted) {
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(
        HttpStatus.OK.value(),
        "Account with id : " + id + " successfully deleted"
      ));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(
        HttpStatus.NOT_FOUND.value(),
        "Account with id : " + id + " not found"
      ));
    }
  }

  @Operation(
    summary = "Get Build information",
    description = "Get Build information that is deployed into accounts microservice"
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "HttpStatus : Ok"
    ),
    @ApiResponse(
      responseCode = "500",
      description = "HttpStatus : Internal Server Error",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class)
      )
    )
  })
  @GetMapping("build-info")
  ResponseEntity<String> getBuildInfo() {
    return ResponseEntity.ok(buildVersion);
  }

  @Operation(
    summary = "Get java version information",
    description = "Get java version information that is deployed into accounts microservice"
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "HttpStatus : Ok"
    ),
    @ApiResponse(
      responseCode = "500",
      description = "HttpStatus : Internal Server Error",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class)
      )
    )
  })
  @GetMapping("java-version")
  ResponseEntity<String> getJavaVersion() {
    return ResponseEntity.ok(environment.getProperty("JAVA_HOME"));
  }

  @Operation(
    summary = "Get contact info",
    description = "Contact info that can be reached out in case of any issue"
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "HttpStatus : Ok"
    ),
    @ApiResponse(
      responseCode = "500",
      description = "HttpStatus : Internal Server Error",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class)
      )
    )
  })
  @GetMapping("contact-info")
  ResponseEntity<AccountsContactInfoDTO> getContactInfo() {
    return ResponseEntity.ok(accountsContactInfoDTO);
  }
}
