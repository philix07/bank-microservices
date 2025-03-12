package com.felix.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicatePhoneNumberException extends RuntimeException {
  public DuplicatePhoneNumberException(String message) {
    super(message);
  }
}
