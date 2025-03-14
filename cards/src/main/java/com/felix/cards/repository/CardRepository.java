package com.felix.cards.repository;

import com.felix.cards.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

  Optional<Card> findByMobileNumber(String mobileNumber);

  Optional<Card> findByCardNumber(String cardNumber);

  boolean existsByMobileNumber(String mobileNumber);

  boolean existsByCardNumber(String cardNumber);
}
