package com.iunetworks.endpoints;

import com.iunetworks.dtos.roledtos.RoleDto;
import com.iunetworks.services.impl.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/roles")
public class RoleEndpoint {

  private final RoleService roleService;

  public RoleEndpoint(RoleService roleService) {
    this.roleService = roleService;
  }

  @GetMapping
  public List<RoleDto> getAll() {
    return roleService.getAll();
  }

  @PostMapping
  public RoleDto create(@RequestBody @Valid RoleDto roleDto) {
    return roleService.create(roleDto);
  }

  @GetMapping("/{id}")
  public RoleDto getById(@PathVariable("id") UUID id) {
    return roleService.getById(id);
  }

  @PutMapping("/{id}")
  public RoleDto update(@PathVariable(name = "id") UUID id, @RequestBody @Valid RoleDto roleDto) {
    return roleService.update(id, roleDto);

  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable(name = "id") UUID id) {
    roleService.delete(id);
  }
}
