package com.iunetworks.endpoints;


import com.iunetworks.dtos.userdtos.CustomerUserDto;
import com.iunetworks.services.impl.CustomerUserService;
import com.iunetworks.util.exceptions.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/customer-users")
public class CustomerUserEndpoint {

  private final CustomerUserService userService;

  @Autowired
  ApplicationEventPublisher eventPublisher;

  public CustomerUserEndpoint(CustomerUserService userService) {
    this.userService = userService;
  }

  @PostMapping("/add-customer-user")
  public CustomerUserDto createCustomerUser(@Valid @RequestBody CustomerUserDto userDto,
                                            BindingResult bindingResult) {
    bindingResult.getModel();
    return userService.create(userDto);
  }


  @PostMapping("/sign-in")
  public ResponseEntity<?> signIn(@RequestBody CustomerUserDto request) {
    return userService.signIn(request.getEmail(), request.getPassword());
  }

  @PreAuthorize("hasAuthority('can_update_customer')")
  @PatchMapping("/update-customer/{id}")
  public CustomerUserDto updateCustomer(@PathVariable(name = "id") UUID id, @RequestBody @Valid CustomerUserDto userDto) {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    CustomerUserDto customerByUserDetails = userService.getCustomerByEmail(userDetails.getUsername());
    if (!customerByUserDetails.getId().equals(id)) {
      throw new ApplicationException(HttpStatus.BAD_REQUEST, "you cannot edit other customer");
    }
    return userService.update(id, userDto);
  }


}

