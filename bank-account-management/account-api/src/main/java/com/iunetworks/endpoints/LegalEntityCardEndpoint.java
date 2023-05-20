package com.iunetworks.endpoints;

import com.iunetworks.dtos.legalEntity.LegalEntityAccountDto;
import com.iunetworks.dtos.legalEntity.LegalEntityAccountRegistrationDto;
import com.iunetworks.dtos.legalEntity.LegalEntityCardDto;
import com.iunetworks.entities.LegalEntityCard;
import com.iunetworks.services.impl.LegalEntityCardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/bank-account/legal-entity-card")
public class LegalEntityCardEndpoint {

  private LegalEntityCardService service;

  public LegalEntityCardEndpoint(LegalEntityCardService service) {
    this.service = service;
  }

  @PatchMapping
  public LegalEntityCardDto create(@PathVariable("id") UUID id, @RequestBody LegalEntityCardDto cardDto) {
    service.create(id, cardDto);
    return cardDto;
  }

  @GetMapping
  public List<LegalEntityCardDto> getAll() {
    return service.getAll();
  }

  @GetMapping("/{id}")
  public LegalEntityCardDto getById(@PathVariable("id") UUID id) {
    return service.getById(id);
  }

  @PutMapping("/{id}")
  public LegalEntityCardDto update(@PathVariable(name = "id") UUID id, @RequestBody LegalEntityCardDto legalEntityCardDto) {
    return service.update(id, legalEntityCardDto);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable(name = "id") UUID id) {
    service.delete(id);
  }
}
