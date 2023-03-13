package com.iunetworks.services.impl;

import com.iunetworks.dtos.userdtos.BankUserDto;
import com.iunetworks.dtos.userdtos.CustomerUserDto;
import com.iunetworks.entities.BankUser;
import com.iunetworks.entities.CustomerUser;
import com.iunetworks.entities.Permission;
import com.iunetworks.entities.Role;
import com.iunetworks.repositories.BankUserRepository;
import com.iunetworks.repositories.CustomerUserRepository;
import com.iunetworks.repositories.RoleRepository;
import com.iunetworks.services.IBankUserService;
import com.iunetworks.util.exceptions.ApplicationException;
import com.iunetworks.util.exceptions.ResourceNotFoundException;
import com.iunetworks.util.security.JwtTokenUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BankUserService implements IBankUserService {

  final Logger log = LoggerFactory.getLogger(BankUserService.class);

  private final BankUserRepository bankUserRepository;
  private final CustomerUserRepository customerUserRepository;
  private final ModelMapper modelMapper;
  private final RoleRepository roleRepository;
  private final JwtTokenUtil jwtTokenUtil;
  private final PasswordEncoder passwordEncoder;


  public BankUserService(BankUserRepository bankUserRepository, CustomerUserRepository customerUserRepository, ModelMapper modelMapper,
                         RoleRepository roleRepository, JwtTokenUtil jwtTokenUtil, PasswordEncoder passwordEncoder) {
    this.bankUserRepository = bankUserRepository;
    this.customerUserRepository = customerUserRepository;
    this.modelMapper = modelMapper;
    this.roleRepository = roleRepository;
    this.jwtTokenUtil = jwtTokenUtil;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public BankUserDto create(BankUserDto bankUserDto) {
    log.info("User has tried to add a bank user");
    if (bankUserRepository.existsByEmail(bankUserDto.getEmail())) {
      log.error("User has tried to add a user with already registered email.");
      throw new ApplicationException(HttpStatus.BAD_REQUEST, "User already exists.");
    }
    BankUser bankUser = modelMapper.map(bankUserDto, BankUser.class);
    final Role role = this.roleRepository.findAdminRole()
      .orElseThrow(() -> new RuntimeException("Something wrong please contact support"));
    bankUser.setRoles(Set.of(role));
    if (!bankUserDto.getPassword().equals(bankUserDto.getRePassword())) {
      throw new ApplicationException(HttpStatus.BAD_REQUEST, "Passwords does not match");
    }
    bankUser.setPassword(passwordEncoder.encode(bankUserDto.getPassword()));
    bankUserRepository.save(bankUser);
    return bankUserDto;
  }

  @Override
  public List<CustomerUserDto> getAllCustomers() {
    log.info("User has tried to get all information about customers");
    return customerUserRepository.findAllByDeletedIsNull().stream()
      .map(customer -> modelMapper.map(customer, CustomerUserDto.class))
      .collect(Collectors.toList());
  }

  @Override
  public CustomerUserDto getCustomerById(UUID id) {
    log.info("User has tried to get information about a customer");
    return modelMapper
      .map((customerUserRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(id))), CustomerUserDto.class);
  }

  @Override
  public List<BankUserDto> getAllBankUsers() {
    log.info("User has tried to get all information about bank users");
    return bankUserRepository.findAllByDeletedIsNull().stream()
      .map(bankUser -> modelMapper.map(bankUser, BankUserDto.class))
      .collect(Collectors.toList());
  }

  @Override
  public BankUserDto getBankUserById(UUID id) {
    log.info("User has tried to get information about a bank user");

    return bankUserRepository.findById(id)
      .map(entity -> modelMapper.map(entity, BankUserDto.class))
      .orElseThrow(() -> new ResourceNotFoundException(id));
  }

  @Override
  public CustomerUserDto updateCustomer(UUID id, CustomerUserDto customerUserDto) {
    log.info("Bank user has tried to update information about a customer");
    CustomerUser user = customerUserRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id : {%s} not found", id.toString())));
    Optional.ofNullable(customerUserDto.getName()).ifPresent(user::setName);
    Optional.ofNullable(customerUserDto.getSurname()).ifPresent(user::setSurname);
    Optional.ofNullable(customerUserDto.getEmail()).ifPresent(user::setEmail);
    Optional.ofNullable(customerUserDto.getRoles()).ifPresent(user::setRoles);


    customerUserRepository.save(user);
    return modelMapper.map(user, CustomerUserDto.class);
  }

  @Override
  public BankUserDto updateBankUser(UUID id, BankUserDto bankUserDto) {
    log.info("Bank user has tried to update information about a bank user");
    BankUser user = bankUserRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id : {%s} not found", id.toString())));
    Optional.ofNullable(bankUserDto.getName()).ifPresent(user::setName);
    Optional.ofNullable(bankUserDto.getSurname()).ifPresent(user::setSurname);
    Optional.ofNullable(bankUserDto.getEmail()).ifPresent(user::setEmail);

    bankUserRepository.save(user);
    return modelMapper.map(user, BankUserDto.class);
  }

  @Override
  public void deleteCustomer(UUID id) {
    log.info("User has tried to delete information about a customer");
    CustomerUser user = customerUserRepository.findById(id).get();
    if (!(user.getDeleted() == null)) {
      log.error("User has tried to delete already deleted customer");
      throw new ApplicationException(HttpStatus.BAD_REQUEST, "User is deleted.");
    }
    user.setDeleted(LocalDateTime.now());
    customerUserRepository.save(user);

  }

  public ResponseEntity<?> signIn(String email, String password) {
    BankUserDto bankUser = modelMapper.map(bankUserRepository.findByEmail(email).get(), BankUserDto.class);
    if (!passwordEncoder.matches(password, bankUser.getPassword())) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
        "Wrong password or email");
    }
    return new ResponseEntity<>(jwtTokenUtil.generateBankUserToken(bankUser, permissions(bankUser.getRoles())), HttpStatus.OK);
  }

  private Set<String> permissions(final Set<Role> roles) {
    final Set<String> permissions = new HashSet<>();

    roles.forEach(role -> permissions.addAll(role.getPermissions().stream()
      .map(Permission::getName)
      .collect(Collectors.toSet())
    ));

    return permissions;
  }


}
