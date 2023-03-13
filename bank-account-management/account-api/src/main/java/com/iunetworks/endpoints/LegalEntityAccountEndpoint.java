package com.iunetworks.endpoints;

import com.iunetworks.dtos.legalEntity.LegalEntityAccountDto;
import com.iunetworks.dtos.legalEntity.LegalEntityAccountRegistrationDto;
import com.iunetworks.services.impl.LegalEntityAccountService;
import com.iunetworks.services.transfer.model.AmountTransaction;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/bank-account/legal-entity-account")
public class LegalEntityAccountEndpoint {

  private final LegalEntityAccountService service;

  public LegalEntityAccountEndpoint(LegalEntityAccountService service) {
    this.service = service;
  }

  @PatchMapping("/add/{id}")
  public LegalEntityAccountRegistrationDto create(@PathVariable("id") UUID id, @RequestBody LegalEntityAccountRegistrationDto legalEntityAccountRegistrationDto) {
    service.create(id, legalEntityAccountRegistrationDto);
    return legalEntityAccountRegistrationDto;
  }

  @GetMapping("/view-all")
  public List<LegalEntityAccountDto> getAll() {
    return service.getAll();
  }

  @GetMapping("/get/{id}")
  public LegalEntityAccountDto getById(@PathVariable("id") UUID id) {
    return service.getById(id);
  }

  @PutMapping("/update/{id}")
  public LegalEntityAccountDto update(@PathVariable(name = "id") UUID id, @RequestBody LegalEntityAccountRegistrationDto legalEntityAccountRegistrationDto) {
    return service.update(id, legalEntityAccountRegistrationDto);
  }

  @PutMapping("/accept/{id}")
  public void accept(@PathVariable("id") UUID id) {
    service.accept(id);
  }

  @PutMapping("/reject/{id}")
  public void reject(@PathVariable("id") UUID id) {
    service.reject(id);
  }

  @DeleteMapping("/delete/{id}")
  public void delete(@PathVariable(name = "id") UUID id) {
    service.delete(id);
  }

  @PutMapping("/transfer/to-account")
  public void transferToAccount(@RequestBody AmountTransaction request) {
    service.transferToAccount(request);
  }

  @PutMapping("/transfer/to-card")
  public void transferToCard(@RequestBody AmountTransaction request) {
    service.transferToCard(request);
  }

  @PutMapping("/transfer")
  public void transfer(@RequestBody AmountTransaction request) {
    service.transferToCard(request);
  }
}
