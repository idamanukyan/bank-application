package com.iunetworks.services;

import com.iunetworks.dtos.permissiondtos.PermissionDto;

import java.util.List;
import java.util.UUID;

public interface IPermissionService {

  List<PermissionDto> getAll();

  PermissionDto getById(final UUID id);
}
