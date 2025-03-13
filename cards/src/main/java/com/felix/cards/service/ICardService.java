package com.felix.cards.service;

import com.felix.cards.dto.CardRequestDTO;
import com.felix.cards.dto.CardResponseDTO;

public interface ICardService {

  void createCard(CardRequestDTO cardRequestDTO);

  CardResponseDTO getCardByMobileNumber(String mobileNumber);

  CardResponseDTO getCardById(Long id);

  void updateCardById(Long id, CardRequestDTO cardRequestDTO);

  void deleteCardById(Long cardId);

}
