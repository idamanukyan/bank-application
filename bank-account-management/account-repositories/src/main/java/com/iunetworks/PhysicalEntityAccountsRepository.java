package com.iunetworks;

import com.iunetworks.entities.Customer;
import com.iunetworks.entities.LegalEntityAccount;
import com.iunetworks.entities.PhysicalEntityAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PhysicalEntityAccountsRepository extends JpaRepository<PhysicalEntityAccount, UUID> {

  Optional<PhysicalEntityAccount> findByAccountNumber(String accountNumber);
}
