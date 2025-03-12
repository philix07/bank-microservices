package com.felix.accounts.service;

import com.felix.accounts.dto.AccountRequestDTO;
import com.felix.accounts.dto.AccountResponseDTO;

public interface IAccountService {

  void createAccounts(AccountRequestDTO accountRequestDTO);

  AccountResponseDTO getAccountByMobileNumber(String mobileNumber);

  AccountResponseDTO getAccountById(Long id);

  void updateAccount(Long id, AccountRequestDTO accountRequestDTO);

  boolean deleteAccount(Long customerId);
}
