package com.iunetworks.endpoints;

import com.iunetworks.dtos.userdtos.BankUserDto;
import com.iunetworks.dtos.userdtos.CustomerUserDto;
import com.iunetworks.services.impl.BankUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bank-users")
public class BankUserEndpoint {

  private final BankUserService userService;

  public BankUserEndpoint(BankUserService userService) {
    this.userService = userService;
  }


  @PreAuthorize("hasAnyAuthority('can_add_admin')")
  @PostMapping("/add-bank-user")
  public BankUserDto createBankUser(@RequestBody @Valid BankUserDto userDto) {
    return userService.create(userDto);
  }

  @PreAuthorize("hasAnyAuthority('can_view_customer')")
  @GetMapping("/view-all-customers")
  public List<CustomerUserDto> getAllCustomers() {
    return userService.getAllCustomers();
  }

  @GetMapping("/customer/{loggedUserId}")
  public CustomerUserDto getCustomerById(@PathVariable("loggedUserId") UUID id) {
    return userService.getCustomerById(id);
  }

  @PreAuthorize("hasAuthority('can_view_admin')")
  @GetMapping("/view-all-bank-users")
  public List<BankUserDto> getAllBankUsers() {
    return userService.getAllBankUsers();
  }

  @PreAuthorize("hasAuthority('can_view_admin')")
  @GetMapping("/bank-user/{id}")
  public BankUserDto getBankUserById(@PathVariable("id") UUID id) {
    return userService.getBankUserById(id);
  }

  @PreAuthorize("hasAuthority('can_update_customer')")
  @PatchMapping("/update-customer/{id}")
  public CustomerUserDto updateCustomer(@PathVariable(name = "id") UUID id, @RequestBody @Valid CustomerUserDto userDto) {
    return userService.updateCustomer(id, userDto);
  }

  @PreAuthorize("hasAuthority('can_update_admin')")
  @PatchMapping("/updateBankUser/{id}")
  public BankUserDto updateBankUser(@PathVariable(name = "id") UUID id, @RequestBody @Valid BankUserDto userDto) {
    return userService.updateBankUser(id, userDto);
  }

  @PreAuthorize("hasAuthority('can_delete_customer')")
  @DeleteMapping("/delete-customer/{id}")
  public void deleteCustomer(@PathVariable("id") UUID id) {
    userService.deleteCustomer(id);
  }

  @PostMapping("/sign-in")
  public ResponseEntity<?> signIn(@RequestBody BankUserDto request) {
    return userService.signIn(request.getEmail(), request.getPassword());
  }


}
