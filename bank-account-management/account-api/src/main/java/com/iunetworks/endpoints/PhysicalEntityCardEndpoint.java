package com.iunetworks.endpoints;

import com.iunetworks.dtos.physicalEntity.PhysicalEntityCardDto;
import com.iunetworks.services.impl.PhysicalEntityCardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/bank-account/physical-entity-card")
public class PhysicalEntityCardEndpoint {

  private PhysicalEntityCardService service;

  public PhysicalEntityCardEndpoint(PhysicalEntityCardService service) {
    this.service = service;
  }

  @PatchMapping
  public PhysicalEntityCardDto create(@PathVariable("id") UUID id, @RequestBody PhysicalEntityCardDto cardDto) {
    service.create(id, cardDto);
    return cardDto;
  }

  @GetMapping
  public List<PhysicalEntityCardDto> getAll() {
    return service.getAll();
  }

  @GetMapping("/{id}")
  public PhysicalEntityCardDto getById(@PathVariable("id") UUID id) {
    return service.getById(id);
  }

  @PutMapping("/{id}")
  public PhysicalEntityCardDto update(@PathVariable(name = "id") UUID id, @RequestBody PhysicalEntityCardDto cardDto) {
    return service.update(id, cardDto);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable(name = "id") UUID id) {
    service.delete(id);
  }
}
