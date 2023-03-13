package com.iunetworks.services;

import com.iunetworks.dtos.roledtos.RoleDto;

import java.util.List;
import java.util.UUID;

public interface IRoleService {

  RoleDto create(RoleDto role);

  List<RoleDto> getAll();

  RoleDto getById(UUID id);

  RoleDto update(UUID id, RoleDto roleDto);

  void delete(UUID id);
}
