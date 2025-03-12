package com.felix.loans.service;

import com.felix.loans.dto.LoanRequestDTO;
import com.felix.loans.dto.LoanResponseDTO;

public interface iLoanService {

  void createLoan(LoanRequestDTO loanRequestDTO);

  LoanResponseDTO getLoanByMobileNumber(String mobileNumber);

  LoanResponseDTO getLoanById(Long id);

  void updateLoan(Long id, LoanRequestDTO loanRequestDTO);

  void deleteLoan(Long loanId);
}
