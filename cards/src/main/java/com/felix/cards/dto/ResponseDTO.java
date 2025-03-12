package com.felix.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
  name = "API Response",
  description = "Successful REST API response format that consumer might expect"
)
public class ResponseDTO {

  @Schema(
    description = "HTTP status code that is being returned from REST API operation"
  )
  private int statusCode;


  @Schema(
    description = "Status message or description that is being returned from REST API operation"
  )
  private String statusMessage;

}
