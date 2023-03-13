package com.iunetworks.security;

import com.iunetworks.dtos.userdtos.BankUserDto;
import com.iunetworks.dtos.userdtos.CustomerUserDto;
import com.iunetworks.repositories.BankUserRepository;
import com.iunetworks.repositories.CustomerUserRepository;
import com.iunetworks.util.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

  private final BankUserRepository bankUserRepository;
  private final CustomerUserRepository customerUserRepository;
  private ModelMapper modelMapper;

  @Autowired
  public UserDetailsService(BankUserRepository bankUserRepository, CustomerUserRepository customerUserRepository,
                            ModelMapper modelMapper) {
    this.bankUserRepository = bankUserRepository;
    this.customerUserRepository = customerUserRepository;
    this.modelMapper = modelMapper;

  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    if (bankUserRepository.existsByEmail(email)) {
      //BankUserDto userDto = map(modelMapper).map(bankUserRepository.findByEmail(email).get(), BankUserDto.class);
      BankUserDto map = modelMapper
        .map((bankUserRepository.findByEmail(email)
          .orElseThrow(() -> new ResourceNotFoundException(email))), BankUserDto.class);
      return new CurrentUser(map);
    } else if (customerUserRepository.existsCustomerUserByEmail(email)) {

      CustomerUserDto userDto = customerUserRepository.findByEmail(email)
        .map(entity -> modelMapper.map(entity, CustomerUserDto.class))
        .orElseThrow(() -> new UsernameNotFoundException("Wrong username"));
      return new CurrentUser(userDto);

    }
    throw new UsernameNotFoundException("The username is not found");
  }
}
