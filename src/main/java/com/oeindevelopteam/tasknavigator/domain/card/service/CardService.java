package com.oeindevelopteam.tasknavigator.domain.card.service;

import com.oeindevelopteam.tasknavigator.domain.card.dto.CardRequestDto;
import com.oeindevelopteam.tasknavigator.domain.card.dto.CardResponseDto;
import com.oeindevelopteam.tasknavigator.domain.card.entity.Card;
import com.oeindevelopteam.tasknavigator.domain.card.repository.CardRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

  private final CardRepository cardRepository;

  public CardResponseDto createdCard(CardRequestDto cardRequestDto, Long columnId) {

    // TODO: 유저아이디 넣어주는거 필요
    Long userId = 1L;

    Card card = new Card(cardRequestDto, columnId, userId);

    cardRepository.save(card);

    return new CardResponseDto(card);
  }

  public List<CardResponseDto> getAllCards() {
    List<Card> cards = cardRepository.findAll();

    return cards.stream()
        .map(card -> new CardResponseDto(card))
        .collect(Collectors.toList());
  }
}
