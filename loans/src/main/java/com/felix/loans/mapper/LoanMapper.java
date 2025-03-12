package com.felix.loans.mapper;

import com.felix.loans.dto.LoanResponseDTO;
import com.felix.loans.entity.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {

  /*
    LoanResponseDTO Structure:
        String mobileNumber;
        String loanNumber;
        String loanType;
        int totalLoan;
        int amountPaid;
        int outstandingAmount;
   */

  public LoanResponseDTO mapEntityToResponse(Loan loan) {
    return new LoanResponseDTO(
      loan.getMobileNumber(),
      loan.getLoanNumber(),
      loan.getLoanType(),
      loan.getTotalLoan(),
      loan.getAmountPaid(),
      loan.getOutstandingAmount()
    );
  }

}
