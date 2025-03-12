package com.felix.accounts.service.impl;

import com.felix.accounts.dto.AccountRequestDTO;
import com.felix.accounts.dto.AccountResponseDTO;
import com.felix.accounts.entity.Account;
import com.felix.accounts.entity.Customer;
import com.felix.accounts.exception.DuplicateCustomerIDException;
import com.felix.accounts.exception.ResourceNotFoundException;
import com.felix.accounts.repository.AccountRepository;
import com.felix.accounts.repository.CustomerRepository;
import com.felix.accounts.service.IAccountService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

  private AccountRepository accountRepository;
  private CustomerRepository customerRepository;

  @Override
  public void createAccounts(AccountRequestDTO accountRequestDTO) {

    // check if the phone number is already exists within the database
    if (customerRepository.findByMobileNumber(accountRequestDTO.getMobileNumber()).isPresent()) {
      throw new DuplicateCustomerIDException("User with Mobile Number : " + accountRequestDTO.getMobileNumber() + " is already exists");
    }

    // creating new customer instance
    Customer customer = new Customer();
    customer.setName(accountRequestDTO.getName());
    customer.setMobileNumber(accountRequestDTO.getMobileNumber());
    customer.setEmail(accountRequestDTO.getEmail());
    Customer savedCustomer = customerRepository.save(customer);

    // generate random number for account number property
    SecureRandom secureRandom = new SecureRandom();
    long randomAccNumber = 1000000000L + (long) (secureRandom.nextDouble() * 9000000000L);

    // creating new accounts instance
    Account account = new Account();
    account.setCustomerId(savedCustomer.getCustomerId());
    account.setAccountNumber(randomAccNumber);
    account.setAccountType(accountRequestDTO.getAccountType());
    account.setBranchAddress(accountRequestDTO.getBranchAddress());
    accountRepository.save(account);

  }

  @Override
  public AccountResponseDTO getAccountByMobileNumber(String mobileNumber) {
    Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
      () -> new ResourceNotFoundException("Customer", "mobile number", mobileNumber)
    );

    Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
      () -> new ResourceNotFoundException("Account", "customer id", customer.getCustomerId().toString())
    );

    return new AccountResponseDTO(
      account.getAccountNumber(),
      account.getAccountType(),
      account.getBranchAddress(),
      customer.getName(),
      customer.getEmail(),
      customer.getMobileNumber()
    );
  }

  @Override
  public AccountResponseDTO getAccountById(Long id) {
    Customer customer = customerRepository.findById(id).orElseThrow(
      () -> new ResourceNotFoundException("Customer", "uid", id.toString())
    );

    Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
      () -> new ResourceNotFoundException("Account", "customer id", customer.getCustomerId().toString())
    );

    return new AccountResponseDTO(
      account.getAccountNumber(),
      account.getAccountType(),
      account.getBranchAddress(),
      customer.getName(),
      customer.getEmail(),
      customer.getMobileNumber()
    );
  }

  @Transactional
  public void updateAccount(Long id, AccountRequestDTO accountRequestDTO) {

    Customer customer = customerRepository.findById(id)
      .map(
        updatedCustomer -> {
          updatedCustomer.setName(accountRequestDTO.getName());
          updatedCustomer.setEmail(accountRequestDTO.getEmail());
          updatedCustomer.setMobileNumber(accountRequestDTO.getMobileNumber());
          return customerRepository.save(updatedCustomer);
        }
      )
      .orElseThrow(
        () -> new ResourceNotFoundException("Customer", "uid : ", id.toString())
      );

    Account account = accountRepository.findByCustomerId(customer.getCustomerId())
      .map(
        newAccount -> {
          // the only field that can be updated within Account entity is AccountType and BranchAddress
          newAccount.setAccountType(accountRequestDTO.getAccountType());
          newAccount.setBranchAddress(accountRequestDTO.getBranchAddress());
          return accountRepository.save(newAccount);
        }
      )
      .orElseThrow(
        () -> new ResourceNotFoundException("Account", "customer id", customer.getCustomerId().toString())
      );

  }

  @Override
  @Transactional
  public boolean deleteAccount(Long customerId) {
    if (customerRepository.existsById(customerId)) {
      customerRepository.deleteById(customerId);
      accountRepository.deleteByCustomerId(customerId);
      return true;
    }
    return false;
  }

}
