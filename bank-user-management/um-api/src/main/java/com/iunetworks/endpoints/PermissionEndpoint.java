package com.iunetworks.endpoints;

import com.iunetworks.dtos.permissiondtos.PermissionDto;
import com.iunetworks.services.impl.PermissionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/permissions")
public class PermissionEndpoint {

  private final PermissionService permissionService;

  public PermissionEndpoint(PermissionService permissionService) {
    this.permissionService = permissionService;
  }

  @GetMapping
  public List<PermissionDto> getAll() {
    return permissionService.getAll();
  }

  @GetMapping("/{id}")
  public PermissionDto getById(@PathVariable("id") UUID id) {
    return permissionService.getById(id);

  }
}
