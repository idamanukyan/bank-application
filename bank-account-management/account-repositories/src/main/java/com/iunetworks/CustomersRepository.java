package com.iunetworks;

import com.iunetworks.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomersRepository extends JpaRepository<Customer, UUID> {

  boolean existsByLoggedUserIdAndDeletedIsNull(UUID loggedId);

  Optional<Customer> findByIdAndDeletedIsNull(UUID id);

}
