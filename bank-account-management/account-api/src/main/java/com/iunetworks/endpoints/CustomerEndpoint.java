package com.iunetworks.endpoints;

import com.iunetworks.dtos.customer.CustomerDto;
import com.iunetworks.dtos.customer.CustomerRegistrationDto;
import com.iunetworks.services.impl.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/bank-account/customers")
public class CustomerEndpoint {


  private final CustomerService customerService;

  public CustomerEndpoint(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping
  public List<CustomerDto> getAll() {
    return customerService.getAll();
  }

  @PatchMapping
  public CustomerDto create(@RequestBody CustomerRegistrationDto customerDto) {
    return customerService.create(customerDto);
  }

  @GetMapping("/{id}")
  public CustomerDto getById(@PathVariable("id") UUID id) {
    return customerService.getById(id);
  }

  @PutMapping("/{id}")
  public CustomerDto update(@PathVariable(name = "id") UUID id, @RequestBody CustomerDto customerDto) {
    return customerService.update(id, customerDto);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable(name = "id") UUID id) {
    customerService.delete(id);
  }

  @GetMapping("/find-name/{id}")
  public String getCustomerNameById(@PathVariable(name = "id") UUID id) {
    return customerService.getCustomerNameById(id);
  }

}
