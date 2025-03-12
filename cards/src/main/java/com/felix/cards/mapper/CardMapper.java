package com.felix.cards.mapper;

import com.felix.cards.dto.CardResponseDTO;
import com.felix.cards.entity.Card;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {

  public CardResponseDTO mapEntityToResponse(Card card) {
    return new CardResponseDTO(
      card.getMobileNumber(),
      card.getCardNumber(),
      card.getCardType(),
      card.getTotalLimit(),
      card.getAmountUsed(),
      card.getAvailableAmount()
    );
  }
}
