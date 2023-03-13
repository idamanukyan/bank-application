package com.iunetworks.services;

import com.iunetworks.dtos.userdtos.BankUserDto;
import com.iunetworks.dtos.userdtos.CustomerUserDto;

import java.util.List;
import java.util.UUID;

public interface IBankUserService {

  BankUserDto create(BankUserDto bankUserDto);

  List<CustomerUserDto> getAllCustomers();

  CustomerUserDto getCustomerById(UUID id);

  List<BankUserDto> getAllBankUsers();

  BankUserDto getBankUserById(UUID id);

  CustomerUserDto updateCustomer(UUID id, CustomerUserDto customerUserDto);

  BankUserDto updateBankUser(UUID id, BankUserDto bankUserDto);

  void deleteCustomer(UUID id);


}
