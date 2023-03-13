package com.iunetworks.repositories;

import com.iunetworks.entities.CustomerUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerUserRepository extends JpaRepository<CustomerUser, UUID> {

  boolean existsCustomerUserByEmail(String email);

  List<CustomerUser> findAllByDeletedIsNull();

  Optional<CustomerUser> findByEmail(String email);

}
