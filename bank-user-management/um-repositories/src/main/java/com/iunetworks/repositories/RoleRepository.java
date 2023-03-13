package com.iunetworks.repositories;

import com.iunetworks.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

  boolean existsRoleByName(String name);

  Optional<Role> findByName(String name);

  Optional<Role> findById(UUID id);

  boolean existsById(UUID id);


  default Optional<Role> findAdminRole() {
    return findByName("ADMIN");
  }

  default Optional<Role> findCustomerRole() {
    return findByName("CUSTOMER");
  }


  boolean existsByName(String name);


}
