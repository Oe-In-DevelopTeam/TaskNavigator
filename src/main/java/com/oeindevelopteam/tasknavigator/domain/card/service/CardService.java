package com.oeindevelopteam.tasknavigator.domain.card.service;

import com.oeindevelopteam.tasknavigator.domain.card.dto.CardRequestDto;
import com.oeindevelopteam.tasknavigator.domain.card.dto.CardResponseDto;
import com.oeindevelopteam.tasknavigator.domain.card.entity.Card;
import com.oeindevelopteam.tasknavigator.domain.card.repository.CardRepository;
import com.oeindevelopteam.tasknavigator.global.exception.CustomException;
import com.oeindevelopteam.tasknavigator.global.exception.ErrorCode;
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

  public CardResponseDto editCardContent(CardRequestDto cardRequestDto, Long cardId) {
    // TODO: 유저 본인이 작성한 유저인지 확인 로직 필요
    // TODO: 유저가 admin이면 수정할 수 있게해주는 로직 필요

    Card card = findCardById(cardId);

    card.editCard(cardRequestDto);

    cardRepository.save(card);

    return new CardResponseDto(card);
  }

  public void deleteCard(Long cardId) {
    // TODO: 유저 본인이 작성한 유저인지 확인 로직 필요
    // TODO: 유저가 admin이면 수정할 수 있게해주는 로직 필요

    Card card = findCardById(cardId);

    cardRepository.delete(card);
  }


  public Card findCardById(Long cardId) {
    return cardRepository.findById(cardId)
        .orElseThrow(() -> new CustomException(ErrorCode.CARD_NOT_FOUND));
  }

}
