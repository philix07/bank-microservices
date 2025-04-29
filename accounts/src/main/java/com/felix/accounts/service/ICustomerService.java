package com.felix.accounts.service;

import com.felix.accounts.dto.CustomerDetailsDTO;

public interface ICustomerService {

  CustomerDetailsDTO fetchCustomerDetails(String mobileNumber);

}
