package com.felix.accounts.mapper;


import com.felix.accounts.dto.AccountResponseDTO;
import com.felix.accounts.entity.Account;
import com.felix.accounts.entity.Customer;

public class AccountMapper {

  public AccountResponseDTO mapEntityToResponse(Account account, Customer customer) {
    return new AccountResponseDTO(
      account.getAccountNumber(),
      account.getAccountType(),
      account.getBranchAddress(),
      customer.getName(),
      customer.getEmail(),
      customer.getMobileNumber()
    );
  }

}
