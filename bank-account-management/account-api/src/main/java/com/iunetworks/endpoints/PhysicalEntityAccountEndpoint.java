package com.iunetworks.endpoints;

import com.iunetworks.dtos.physicalEntity.PhysicalEntityAccountDto;
import com.iunetworks.services.impl.PhysicalEntityAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/bank-account/physical-entity-account")
public class PhysicalEntityAccountEndpoint {

  private PhysicalEntityAccountService service;


  public PhysicalEntityAccountEndpoint(PhysicalEntityAccountService service) {
    this.service = service;
  }

  @PatchMapping
  public PhysicalEntityAccountDto create(@PathVariable("id") UUID id, @RequestBody PhysicalEntityAccountDto accountDto) {
    service.create(id, accountDto);
    return accountDto;
  }

  @GetMapping
  public List<PhysicalEntityAccountDto> getAll() {
    return service.getAll();
  }

  @GetMapping("/{id}")
  public PhysicalEntityAccountDto getById(@PathVariable("id") UUID id) {
    return service.getById(id);
  }

  @PutMapping("/{id}")
  public PhysicalEntityAccountDto update(@PathVariable(name = "id") UUID id, @RequestBody PhysicalEntityAccountDto accountDto) {
    return service.update(id, accountDto);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable(name = "id") UUID id) {
    service.delete(id);
  }
}
