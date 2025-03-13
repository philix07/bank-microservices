package com.felix.loans.service;

import com.felix.loans.dto.LoanRequestDTO;
import com.felix.loans.dto.LoanResponseDTO;

public interface ILoanService {

  void createLoan(LoanRequestDTO loanRequestDTO);

  LoanResponseDTO getLoanByMobileNumber(String mobileNumber);

  LoanResponseDTO getLoanById(Long id);

  void updateLoanById(Long id, LoanRequestDTO loanRequestDTO);

  void deleteLoanById(Long loanId);
}
