package com.felix.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicateCustomerIDException extends RuntimeException {
  public DuplicateCustomerIDException(String message) {
    super(message);
  }
}
