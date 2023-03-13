package com.iunetworks.repositories;

import com.iunetworks.entities.BankUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BankUserRepository extends JpaRepository<BankUser, UUID> {


  boolean existsByEmail(String email);

  List<BankUser> findAllByDeletedIsNull();

  Optional<BankUser> findByEmail(String email);
}
