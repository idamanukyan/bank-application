package com.iunetworks;

import com.iunetworks.entities.LegalEntityAccount;
import com.iunetworks.entities.LegalEntityCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LegalEntityCardsRepository extends JpaRepository<LegalEntityCard, UUID> {

  Optional<LegalEntityCard> findByCardNumber(String cardNumber);

}
