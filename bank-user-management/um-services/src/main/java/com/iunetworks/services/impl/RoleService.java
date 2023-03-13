package com.iunetworks.services.impl;


import com.iunetworks.dtos.roledtos.RoleDto;
import com.iunetworks.entities.Role;
import com.iunetworks.repositories.RoleRepository;
import com.iunetworks.services.IRoleService;
import com.iunetworks.util.exceptions.ApplicationException;
import com.iunetworks.util.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoleService implements IRoleService {

  final Logger log = LoggerFactory.getLogger(PermissionService.class);

  private final RoleRepository roleRepository;
  private final ModelMapper modelMapper;

  public RoleService(RoleRepository roleRepository, ModelMapper modelMapper) {
    this.roleRepository = roleRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public RoleDto create(RoleDto role) {
    log.info("User has tried to add a role");
    if (roleRepository.existsRoleByName(role.getName())) {
      log.error("User has tried to add a role with already registered name.");
      throw new ApplicationException(HttpStatus.BAD_REQUEST, "Role already exists.");
    }
    roleRepository.save(modelMapper.map(role, Role.class));
    return role;
  }


  @Override
  public List<RoleDto> getAll() {
    log.info("User has tried to get all information about roles");
    return roleRepository.findAll().stream()
      .map(role -> modelMapper.map(role, RoleDto.class))
      .collect(Collectors.toList());
  }

  @Override
  public RoleDto getById(UUID id) {
    log.info("User has tried to get information about a role");
    return modelMapper
      .map((roleRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(id))), RoleDto.class);
  }

  @Override
  public RoleDto update(UUID id, RoleDto roleDto) {
    log.info("User has tried to update information about a role");
    Role role = roleRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException(String.format("Role with id : {%s} not found", id.toString())));
    role.setName(roleDto.getName());
    role.setDescription(roleDto.getDescription());
    return modelMapper.map(roleRepository.save(role), RoleDto.class);
  }

  @Override
  public void delete(UUID id) {
    log.info("User has tried to delete information about a role");
    if (roleRepository.findById(id).isEmpty()) {
      log.error("User has tried to delete already deleted role");
    }
    roleRepository.deleteById(id);
  }
}
