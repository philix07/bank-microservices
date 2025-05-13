package com.felix.accounts.service.impl;

import com.felix.accounts.dto.AccountResponseDTO;
import com.felix.accounts.dto.CardResponseDTO;
import com.felix.accounts.dto.CustomerDetailsDTO;
import com.felix.accounts.dto.LoanResponseDTO;
import com.felix.accounts.entity.Account;
import com.felix.accounts.entity.Customer;
import com.felix.accounts.exception.ResourceNotFoundException;
import com.felix.accounts.mapper.AccountMapper;
import com.felix.accounts.repository.AccountRepository;
import com.felix.accounts.repository.CustomerRepository;
import com.felix.accounts.service.ICustomerService;
import com.felix.accounts.service.client.CardsFeignClient;
import com.felix.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

  private AccountMapper accountMapper;

  private AccountRepository accountRepository;
  private CustomerRepository customerRepository;

  // invoke other microservices
  private CardsFeignClient cardsFeignClient;
  private LoansFeignClient loansFeignClient;

  @Override
  public CustomerDetailsDTO fetchCustomerDetails(String mobileNumber, String correlationId) {
    Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
      () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber.toString())
    );

    Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
      () -> new ResourceNotFoundException("Account", "customer id", customer.getCustomerId().toString())
    );

    AccountResponseDTO accountResponseDTO = accountMapper.mapEntityToResponse(account, customer);

    ResponseEntity<LoanResponseDTO> loanResponseEntity = loansFeignClient.getLoanByMobileNumber(correlationId, mobileNumber);
    LoanResponseDTO loanResponseDTO = loanResponseEntity.getBody();

    ResponseEntity<CardResponseDTO> cardResponseEntity = cardsFeignClient.getCardByMobileNumber(correlationId, mobileNumber);
    CardResponseDTO cardResponseDTO = cardResponseEntity.getBody();

    CustomerDetailsDTO customerDetailsDTO = new CustomerDetailsDTO();
    customerDetailsDTO.setAccountsDto(accountResponseDTO);
    customerDetailsDTO.setLoansDto(loanResponseDTO);
    customerDetailsDTO.setCardsDto(cardResponseDTO);

    return customerDetailsDTO;
  }
}
