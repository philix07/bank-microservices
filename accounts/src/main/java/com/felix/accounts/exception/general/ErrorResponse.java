package com.felix.accounts.exception.general;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Schema(
  name = "Error Response",
  description = "Error REST API response format that consumer might expect"
)
public class ErrorResponse {

  @Schema(
    description = "HTTP status code that is being returned from the error operation"
  )
  private int status;

  @Schema(
    description = "DateTime of when the error occurred",
    example = "2025-03-11T20:45:30.123456"
  )
  private LocalDateTime timestamp;

  @Schema(
    description = "The class which triggers the error",
    example = "DuplicateCustomerIDException"
  )
  private String error;

  @Schema(
    description = "Error message to describe what might went wrong from the operation",
    example = "Customer with id : 1 already exists"
  )
  private String message;


  @Schema(
    description = "describe API end point where the error occur",
    example = "/v1/account"
  )
  private String path;

}
