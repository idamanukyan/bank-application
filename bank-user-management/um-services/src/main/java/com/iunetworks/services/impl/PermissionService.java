package com.iunetworks.services.impl;


import com.iunetworks.dtos.permissiondtos.PermissionDto;
import com.iunetworks.util.exceptions.ResourceNotFoundException;
import com.iunetworks.repositories.PermissionRepository;
import com.iunetworks.services.IPermissionService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PermissionService implements IPermissionService {

  final Logger log = LoggerFactory.getLogger(PermissionService.class);

  private final PermissionRepository permissionRepository;
  private final ModelMapper modelMapper;

  public PermissionService(PermissionRepository permissionRepository, ModelMapper modelMapper) {
    this.permissionRepository = permissionRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<PermissionDto> getAll() {
    log.info("User has tried to get all information about permissions");
    return permissionRepository.findAll().stream()
      .map(prm -> modelMapper.map(prm, PermissionDto.class))
      .collect(Collectors.toList());
  }

  @Override
  public PermissionDto getById(final UUID id) {
    log.info("User has tried to get information about a permission");
    return modelMapper
      .map((permissionRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(id))), PermissionDto.class);
  }


}
