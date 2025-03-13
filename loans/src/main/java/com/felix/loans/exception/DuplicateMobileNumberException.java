package com.felix.loans.exception;

public class DuplicateMobileNumberException extends RuntimeException {
  public DuplicateMobileNumberException(String message) {
    super(message);
  }
}
