package com.felix.loans.repository;

import com.felix.loans.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

  Optional<Loan> findByLoanNumber(String loanNumber);

  Optional<Loan> findByMobileNumber(String mobileNumber);

  boolean existsByLoanNumber(String loanNumber);

  boolean existsByMobileNumber(String mobileNumber);

}
