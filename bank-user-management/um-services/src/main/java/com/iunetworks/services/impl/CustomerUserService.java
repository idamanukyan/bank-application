package com.iunetworks.services.impl;

import com.iunetworks.dtos.userdtos.CustomerUserDto;
import com.iunetworks.entities.CustomerUser;
import com.iunetworks.entities.Permission;
import com.iunetworks.entities.Role;
import com.iunetworks.repositories.CustomerUserRepository;
import com.iunetworks.repositories.RoleRepository;
import com.iunetworks.services.ICustomerUserService;
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

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerUserService implements ICustomerUserService {

  final Logger log = LoggerFactory.getLogger(PermissionService.class);

  private final CustomerUserRepository userRepository;
  private final ModelMapper modelMapper;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenUtil jwtTokenUtil;


  public CustomerUserService(CustomerUserRepository userRepository, ModelMapper modelMapper, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil) {
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtTokenUtil = jwtTokenUtil;

  }

  @Override
  public CustomerUserDto create(CustomerUserDto customerUserDto) {
    log.info("User has tried to add a customer user");
    if (userRepository.existsCustomerUserByEmail(customerUserDto.getEmail())) {
      log.error("User has tried to add a user with already registered email.");
      throw new ApplicationException(HttpStatus.BAD_REQUEST, "User already exists.");
    }
    CustomerUser customerUser = modelMapper.map(customerUserDto, CustomerUser.class);
    final Role role = this.roleRepository.findCustomerRole()
      .orElseThrow(() -> new RuntimeException("Something wrong please contact support"));
    customerUser.setRoles(Set.of(role));
    if (!customerUserDto.getPassword().equals(customerUserDto.getRePassword())) {
      throw new ApplicationException(HttpStatus.BAD_REQUEST, "Passwords does not match");
    }
    customerUser.setPassword(passwordEncoder.encode(customerUserDto.getPassword()));
    userRepository.save(customerUser);
    return customerUserDto;
  }

  public CustomerUserDto verifyEmail(String activationCode, CustomerUserDto currentUser) {
    Optional<CustomerUser> userByEmail = userRepository.findByEmail(currentUser.getEmail());
    if (userByEmail.isEmpty()) {
      return null;
    }
    CustomerUser user = userByEmail.get();
 /*   if()
    if (user.getActiveCode().equals(UUID.fromString(activationCode))) {
      user.setEmailVerified(true);
      user.setActiveCode(null);
      return userRepository.save(user);
    }*/
    return null;
  }

  @Override
  public CustomerUserDto update(UUID id, CustomerUserDto customerUserDto) {
    log.info("User has tried to update information about a customer");
    CustomerUser user = userRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id : {%s} not found", id.toString())));
    Optional.ofNullable(customerUserDto.getName()).ifPresent(user::setName);
    Optional.ofNullable(customerUserDto.getSurname()).ifPresent(user::setSurname);
    userRepository.save(user);
    return modelMapper.map(user, CustomerUserDto.class);
  }

  public ResponseEntity<?> signIn(String email, String password) {
    CustomerUserDto customerUserDto = modelMapper.map(userRepository.findByEmail(email).get(), CustomerUserDto.class);

    if (!passwordEncoder.matches(password, customerUserDto.getPassword())) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
        "Wrong password or email");
    }
    return new ResponseEntity<>(jwtTokenUtil.generateCustomerUserToken(customerUserDto, permissions(customerUserDto.getRoles())), HttpStatus.OK);
  }

  private Set<String> permissions(final Set<Role> roles) {
    final Set<String> permissions = new HashSet<>();

    roles.forEach(role -> permissions.addAll(role.getPermissions().stream()
      .map(Permission::getName)
      .collect(Collectors.toSet())
    ));

    return permissions;
  }

  public CustomerUserDto getCustomerByEmail(String email) {
    log.info("User has tried to get information about a customer");
    return modelMapper
      .map((userRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException(email))), CustomerUserDto.class);
  }

}
