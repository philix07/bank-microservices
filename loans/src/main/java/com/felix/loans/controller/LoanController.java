package com.felix.loans.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
  name = "CRUD REST APIs for Loan in MyBank",
  description = "CREATE, READ, UPDATE and DELETE 'loan' details"
)
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("v1")
public class LoanController {
}
