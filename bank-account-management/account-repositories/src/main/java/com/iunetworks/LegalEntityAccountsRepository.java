package com.iunetworks;

import com.iunetworks.entities.LegalEntityAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LegalEntityAccountsRepository extends JpaRepository<LegalEntityAccount, UUID> {

  Optional<LegalEntityAccount> findByAccountNumber(String accountNumber);
}
