package com.iunetworks;

import com.iunetworks.entities.LegalEntityCard;
import com.iunetworks.entities.PhysicalEntityCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PhysicalEntityCardsRepository extends JpaRepository<PhysicalEntityCard, UUID> {

  Optional<PhysicalEntityCard> findByCardNumber(String cardNumber);
}
