package com.felix.cards.controller;

import com.felix.cards.dto.CardRequestDTO;
import com.felix.cards.dto.CardResponseDTO;
import com.felix.cards.dto.ResponseDTO;
import com.felix.cards.exception.ErrorResponse;
import com.felix.cards.service.ICardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
  name = "CRUD REST APIs for Card in MyBank",
  description = "CREATE, READ, UPDATE and DELETE 'card' details"
)
@AllArgsConstructor
@RestController
@RequestMapping(path = "v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class CardController {

  ICardService cardService;

  @Operation(
    summary = "Get Card by Mobile Number REST API",
    description = "REST API to fetch Card data by using customer's mobile number"
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "HTTP Status OK"
    ),
    @ApiResponse(
      responseCode = "404",
      description = "HTTP Status Not_Found",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class)
      )
    )
  })
  @GetMapping("cards")
  public ResponseEntity<CardResponseDTO> getCardByMobileNumber(@RequestParam String mobileNumber) {
    return ResponseEntity.ok(cardService.getCardByMobileNumber(mobileNumber));
  }

  @Operation(
    summary = "Get Card by Card ID REST API",
    description = "REST API to fetch Card data by using loan id"
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "HTTP Status OK"
    ),
    @ApiResponse(
      responseCode = "404",
      description = "HTTP Status Not_Found",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class)
      )
    )
  })
  @GetMapping("cards/{id}")
  public ResponseEntity<CardResponseDTO> getCardById(@PathVariable Long id) {
    return ResponseEntity.ok(cardService.getCardById(id));
  }

  @Operation(
    summary = "Create new Card REST API",
    description = "REST API to create new Card inside MyBank"
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "HTTP Status OK"
    ),
    @ApiResponse(
      responseCode = "409",
      description = "HTTP Status Conflict",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class)
      )
    )
  })
  @PostMapping("cards")
  public ResponseEntity<ResponseDTO> createNewCard(@Valid @RequestBody CardRequestDTO cardRequestDTO) {
    cardService.createCard(cardRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED)
      .body(new ResponseDTO(
        201,
        "New card resource successfully created"
      ));
  }

  @Operation(
    summary = "Update Card by id REST API",
    description = "REST API to create new Card data inside MyBank"
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "HTTP Status OK"
    ),
    @ApiResponse(
      responseCode = "404",
      description = "HTTP Status Not_Found",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class)
      )
    ),
    @ApiResponse(
      responseCode = "409",
      description = "HTTP Status Conflict",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class)
      )
    )
  })
  @PatchMapping("cards/{id}")
  public ResponseEntity<ResponseDTO> updateCardById(@PathVariable Long id, @Valid @RequestBody CardRequestDTO cardRequestDTO) {
    cardService.updateCardById(id, cardRequestDTO);
    return ResponseEntity.ok(new ResponseDTO(
      200,
      "Loan data with id : " + id + " successfully updated"
    ));
  }

  @Operation(
    summary = "Delete Loan by id REST API",
    description = "REST API to create new loan inside MyBank"
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "HTTP Status OK"
    ),
    @ApiResponse(
      responseCode = "404",
      description = "HTTP Status Not_Found",
      content = @Content(
        schema = @Schema(implementation = ErrorResponse.class)
      )
    )
  })
  @DeleteMapping("cards/{id}")
  public ResponseEntity<ResponseDTO> deleteCardById(@PathVariable Long id) {
    cardService.deleteCardById(id);
    return ResponseEntity.ok(new ResponseDTO(
      200,
      "Loan data with id : " + id + " successfully deleted"
    ));
  }

}
