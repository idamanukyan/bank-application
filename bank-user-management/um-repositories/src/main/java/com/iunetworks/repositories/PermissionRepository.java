package com.iunetworks.repositories;

import com.iunetworks.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;
import java.util.UUID;

public interface PermissionRepository extends JpaRepository<Permission, UUID> {

  Set<Permission> findAllByNameIn(Set<String> names);


}
