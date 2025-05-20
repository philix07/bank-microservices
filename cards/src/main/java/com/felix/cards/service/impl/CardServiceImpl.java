package com.felix.cards.service.impl;

import com.felix.cards.dto.CardRequestDTO;
import com.felix.cards.dto.CardResponseDTO;
import com.felix.cards.entity.Card;
import com.felix.cards.exception.DuplicateMobileNumberException;
import com.felix.cards.exception.ResourceNotFoundException;
import com.felix.cards.mapper.CardMapper;
import com.felix.cards.repository.CardRepository;
import com.felix.cards.service.ICardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@AllArgsConstructor
public class CardService implements ICardService {

  CardRepository cardRepository;
  CardMapper cardMapper;

  @Override
  public void createCard(CardRequestDTO cardRequestDTO) {
    // Generate a random 12-digit number
    long cardNumber;
    SecureRandom secureRandom = new SecureRandom();
    do {
      cardNumber = 100000000000L + (long) (secureRandom.nextDouble() * 900000000000L);
    } while (cardRepository.existsByCardNumber(String.valueOf(cardNumber)));

    if (cardRepository.existsByMobileNumber(cardRequestDTO.getMobileNumber())) {
      throw new DuplicateMobileNumberException("Mobile number already registered");
    }

    Card newCard = new Card();
    newCard.setCardType("Debit");
    newCard.setCardNumber(String.valueOf(cardNumber));
    newCard.setMobileNumber(cardRequestDTO.getMobileNumber());
    newCard.setTotalLimit(cardRequestDTO.getTotalLimit());
    newCard.setAmountUsed(cardRequestDTO.getAmountUsed());
    newCard.setAvailableAmount(cardRequestDTO.getAvailableAmount());

    cardRepository.save(newCard);
  }

  @Override
  public CardResponseDTO getCardByMobileNumber(String mobileNumber) {
    return cardMapper
      .mapEntityToResponse(
        cardRepository.findByMobileNumber(mobileNumber)
          .orElseThrow(() -> new ResourceNotFoundException("Card", "mobile number", mobileNumber))
      );
  }

  @Override
  public CardResponseDTO getCardById(Long id) {
    return cardMapper
      .mapEntityToResponse(
        cardRepository.findById(id)
          .orElseThrow(() -> new ResourceNotFoundException("Card", "card id", id.toString()))
      );
  }

  @Override
  public void updateCardById(Long id, CardRequestDTO cardRequestDTO) {
    cardRepository.findById(id).map(
      updatedCard -> {
        updatedCard.setAvailableAmount(cardRequestDTO.getAvailableAmount());
        updatedCard.setAmountUsed(cardRequestDTO.getAmountUsed());
        updatedCard.setTotalLimit(cardRequestDTO.getTotalLimit());
        updatedCard.setMobileNumber(cardRequestDTO.getMobileNumber());
        return cardRepository.save(updatedCard);
      }
    );
  }

  @Override
  public void deleteCardById(Long cardId) {
    cardRepository.deleteById(cardId);
  }
}
