package com.felix.loans.service.impl;

import com.felix.loans.dto.LoanRequestDTO;
import com.felix.loans.dto.LoanResponseDTO;
import com.felix.loans.entity.Loan;
import com.felix.loans.exception.LoanAlreadyExistsException;
import com.felix.loans.exception.ResourceNotFoundException;
import com.felix.loans.mapper.LoanMapper;
import com.felix.loans.repository.LoanRepository;
import com.felix.loans.service.ILoanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements ILoanService {

  private LoanRepository loanRepository;
  private LoanMapper loanMapper;

  @Override
  public void createLoan(LoanRequestDTO loanRequestDTO) {
    // Generate a random 12-digit number
    long randomAccNumber;
    SecureRandom secureRandom = new SecureRandom();
    do {
      randomAccNumber = 100000000000L + (long) (secureRandom.nextDouble() * 900000000000L);
    } while (loanRepository.findByLoanNumber(String.valueOf(randomAccNumber)).isPresent());

    Loan loan = new Loan();
    loan.setLoanNumber(String.valueOf(randomAccNumber));
    loan.setLoanType(loanRequestDTO.getLoanType());
    loan.setTotalLoan(loanRequestDTO.getTotalLoan());
    loan.setAmountPaid(loanRequestDTO.getAmountPaid());
    loan.setMobileNumber(loanRequestDTO.getMobileNumber());
    loan.setOutstandingAmount(loanRequestDTO.getOutstandingAmount());

    loanRepository.save(loan);
  }

  @Override
  public LoanResponseDTO getLoanByMobileNumber(String mobileNumber) {
    Loan loan = loanRepository.findByMobileNumber(mobileNumber).orElseThrow(
      () -> new ResourceNotFoundException("Loan", "mobile number", mobileNumber)
    );

    return loanMapper.mapEntityToResponse(loan);
  }

  @Override
  public LoanResponseDTO getLoanById(Long id) {
    Loan loan = loanRepository.findById(id).orElseThrow(
      () -> new ResourceNotFoundException("Loan", "id", id.toString())
    );

    return loanMapper.mapEntityToResponse(loan);
  }

  @Override
  public void updateLoan(Long id, LoanRequestDTO loanRequestDTO) {
    Loan loan = loanRepository.findById(id)
      .map(
        newLoan -> {
          newLoan.setLoanType(loanRequestDTO.getLoanType());
          newLoan.setTotalLoan(loanRequestDTO.getTotalLoan());
          newLoan.setAmountPaid(loanRequestDTO.getAmountPaid());
          newLoan.setMobileNumber(loanRequestDTO.getMobileNumber());
          newLoan.setOutstandingAmount(loanRequestDTO.getOutstandingAmount());
          return loanRepository.save(newLoan);
        }
      )
      .orElseThrow(
        () -> new ResourceNotFoundException("Loan", "id", id.toString())
      );
  }

  @Override
  public void deleteLoan(Long loanId) {
    Loan loan = loanRepository.findById(loanId).orElseThrow(
      () -> new ResourceNotFoundException("Loan", "id", loanId.toString())
    );

    loanRepository.delete(loan);
  }
}
