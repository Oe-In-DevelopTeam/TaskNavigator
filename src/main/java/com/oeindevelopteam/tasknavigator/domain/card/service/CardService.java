package com.oeindevelopteam.tasknavigator.domain.card.service;

import com.oeindevelopteam.tasknavigator.domain.board.entity.UserBoardMatches;
import com.oeindevelopteam.tasknavigator.domain.board.repository.UserBoardMatchesRepository;
import com.oeindevelopteam.tasknavigator.domain.card.dto.CardRequestDto;
import com.oeindevelopteam.tasknavigator.domain.card.dto.CardResponseDto;
import com.oeindevelopteam.tasknavigator.domain.card.dto.CardTagEditRequestDto;
import com.oeindevelopteam.tasknavigator.domain.card.entity.Card;
import com.oeindevelopteam.tasknavigator.domain.card.entity.CardTag;
import com.oeindevelopteam.tasknavigator.domain.card.entity.CardTagMatches;
import com.oeindevelopteam.tasknavigator.domain.card.repository.CardRepository;
import com.oeindevelopteam.tasknavigator.domain.card.repository.CardTagRepository;
import com.oeindevelopteam.tasknavigator.domain.user.entity.User;
import com.oeindevelopteam.tasknavigator.domain.user.entity.UserRoleMatches;
import com.oeindevelopteam.tasknavigator.domain.user.repository.UserRoleMatchesRepository;
import com.oeindevelopteam.tasknavigator.global.exception.CustomException;
import com.oeindevelopteam.tasknavigator.global.exception.ErrorCode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardService {

  private final CardRepository cardRepository;
  private final CardTagRepository cardTagRepository;
  private final UserBoardMatchesRepository userBoardMatchesRepository;
  private final UserRoleMatchesRepository userRoleMatchesRepository;

  @Transactional
  public CardResponseDto createdCard(CardRequestDto cardRequestDto, Long boardId, Long columnId,
      User user) {
    checkUserPermission(user, boardId);

    Long userId = user.getId();

    Card card = new Card(cardRequestDto, boardId, columnId, userId);

    cardRepository.save(card);

    Set<CardTagMatches> tagMatches = new HashSet<>();
    for (String tagName : cardRequestDto.getTags()) {
      CardTag tag = findCardTagByName(tagName);

      if (tag == null) {
        throw new CustomException(ErrorCode.TAG_NOT_FOUND);
      }

      CardTagMatches matches = new CardTagMatches(card, tag);
      tagMatches.add(matches);

    }

    card.setTagMatches(tagMatches);
    cardRepository.save(card);

    return new CardResponseDto(card);
  }

  @Transactional
  public CardResponseDto editCardContent(CardRequestDto cardRequestDto, Long cardId, User user) {

    Card card = cardRepository.findById(cardId)
        .orElseThrow(() -> new CustomException(ErrorCode.CARD_NOT_FOUND));

    checkUserPermission(user, card.getBoardId());

    if (!findUserisAdmin(user)) {
      if (user.getId() != card.getUserId()) {
        throw new CustomException(ErrorCode.CARD_ACCESS_DENIED);
      }
    }

    card.editCard(cardRequestDto);

    cardRepository.save(card);

    return new CardResponseDto(card);
  }

  @Transactional
  public CardResponseDto editCardTags(Long cardId, CardTagEditRequestDto cardTagEditRequestDto,
      User user) {
    Card card = getCardById(cardId);

    checkUserPermission(user, card.getBoardId());

    if (!findUserisAdmin(user)) {
      if (user.getId() != card.getUserId()) {
        throw new CustomException(ErrorCode.CARD_ACCESS_DENIED);
      }
    }

    Set<CardTag> cardTags = findCardTagByNameIn(cardTagEditRequestDto.getTags());

    // 새로운 태그들을 추가
    for (CardTag cardTag : cardTags) {
      if (!card.getTagMatches().contains(new CardTagMatches(card, cardTag))) {
        card.addCardTag(cardTag);
      }
    }

    List<CardTagMatches> removeCardTagMatches = new ArrayList<>();

    // 태그 삭제
    for (CardTagMatches cardTagMatches : card.getTagMatches()) {
      if (!cardTags.contains(cardTagMatches.getTag())) {
        removeCardTagMatches.add(cardTagMatches);
      }
    }

    removeCardTagMatches.forEach(card::removeCardTag);

    // 수정된 카드 정보를 반환
    return new CardResponseDto(card);
  }

  public CardResponseDto getCardDetail(Long cardId, User user) {
    Card card = getCardById(cardId);

    checkUserPermission(user, card.getBoardId());

    if (!findUserisAdmin(user)) {
      if (user.getId() != card.getUserId()) {
        throw new CustomException(ErrorCode.CARD_ACCESS_DENIED);
      }
    }

    return new CardResponseDto(card);
  }

  public void deleteCard(Long cardId, User user) {
    Card card = getCardById(cardId);
    checkUserPermission(user, card.getBoardId());

    cardRepository.delete(card);
  }

  public List<CardResponseDto> getCardsByTag(String tag, User user) {

    List<Long> boardIds;

    List<List<Card>> cardLists = new ArrayList<>();

    if (!findUserisAdmin(user)) {
      boardIds = getInvitedBoardIds(user.getId());
      cardLists = getCardListsByBoardIds(boardIds);
    } else {
      List<Card> cards = cardRepository.findAll();
      cardLists.add(cards);
    }

    List<Card> resultCards = new ArrayList<>();

    for (List<Card> cards : cardLists) {
      for (Card card : cards) {
        Set<CardTagMatches> cardTagMatches = card.getTagMatches();

        List<String> tagNames = cardTagMatches.stream()
            .map(cardTagMatch -> cardTagMatch.getTag().getName())
            .collect(Collectors.toList());

        if (tagNames.contains(tag)) {
          resultCards.add(card);
        }
      }
    }

    if (resultCards.isEmpty()) {
      throw new CustomException(ErrorCode.CARD_NOT_FOUND);
    }

    return resultCards.stream().map(card -> new CardResponseDto(card)).collect(Collectors.toList());
  }


  public List<CardResponseDto> getCardsByManager(String manager, User user) {
    List<Card> resultCards = new ArrayList<>();
    List<List<Card>> cardLists = new ArrayList<>();

    if (!findUserisAdmin(user)) {
      List<Long> boardIds = getInvitedBoardIds(user.getId());
      cardLists = getCardListsByBoardIds(boardIds);
    } else {
      List<Card> cards = cardRepository.findAll();
      cardLists.add(cards);
    }

    for (List<Card> cards : cardLists) {
      for (Card card : cards) {
        if (card.getManager() != null && card.getManager().equals(manager)) {
          resultCards.add(card);
        }
      }
    }

    if (resultCards.isEmpty()) {
      throw new CustomException(ErrorCode.CARD_NOT_FOUND);
    }

    return resultCards.stream().map(card -> new CardResponseDto(card))
        .collect(Collectors.toList());
  }

  public List<CardResponseDto> getAllCardsByInvited(User user) {
    if (!findUserisAdmin(user)) {
      List<Long> boardIds = getInvitedBoardIds(user.getId());
      List<List<Card>> cardLists = getCardListsByBoardIds(boardIds);

      List<Card> resultCards = new ArrayList<>();

      for (List<Card> cards : cardLists) {
        for (Card card : cards) {
          resultCards.add(card);
        }
      }

      if (resultCards.isEmpty()) {
        throw new CustomException(ErrorCode.CARD_NOT_FOUND);
      }

      return resultCards.stream().map(card -> new CardResponseDto(card))
          .collect(Collectors.toList());
    } else {
      List<Card> cards = cardRepository.findAll();

      return cards.stream()
          .map(card -> new CardResponseDto(card))
          .collect(Collectors.toList());
    }
  }


  private List<Long> getInvitedBoardIds(Long userId) {
    // TODO: 오류 정리되면 삭제 할 예정
    // List<UserBoardMatches> userBoardMatches = user.getUserBoardMatchesList();

    // 본인이 초대받은 모든 보드의 카드 조회
    List<UserBoardMatches> userBoardMatches = userBoardMatchesRepository.findByUserId(userId)
        .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

    // 보드 아이디들 받아오기
    List<Long> boardIds = userBoardMatches.stream()
        .map(userBoardMatch -> userBoardMatch.getBoard().getId())
        .collect(Collectors.toList());

    return boardIds;
  }

  private List<List<Card>> getCardListsByBoardIds(List<Long> boardIds) {
    List<List<Card>> cardLists = new ArrayList<>();

    for (Long boardId : boardIds) {
      List<Card> cards = cardRepository.findByBoardId(boardId);

      if (!cards.isEmpty()) {
        cardLists.add(cards);
      }
    }

    if (cardLists.isEmpty()) {
      throw new CustomException(ErrorCode.CARD_NOT_FOUND);
    }

    return cardLists;
  }

  private void checkUserPermission(User user, Long boardId) {
    // admin이 아닐때
    if (!findUserisAdmin(user)) {
      List<Long> boardIds = getInvitedBoardIds(user.getId());

      boolean haveBoardId = false;

      for (Long l : boardIds) {
        if (boardId == l) {
          haveBoardId = true;
          break;
        }
      }
      if (!haveBoardId) {
        throw new CustomException(ErrorCode.NOT_INVITED_USER);
      }
    }
  }

  private boolean findUserisAdmin(User user) {

    // userId 로 Board 권한 체크
    List<UserRoleMatches> roles = userRoleMatchesRepository.findByUser(user);

    Boolean isAdmin = false;

    for (UserRoleMatches l : roles) {
      if (l.getUserRole().getRole().equals("MANAGER")) {
        isAdmin = true;
        break;
      }
    }
    return isAdmin;
  }

  public Set<CardTag> findCardTagByNameIn(Set<String> cardTagNames) {
    return cardTagRepository.findAllByNameIn(cardTagNames);
  }

  public CardTag findCardTagByName(String cardTagName) {
    return cardTagRepository.findByName(cardTagName)
        .orElseThrow(() -> new CustomException(ErrorCode.CARD_TAG_NOT_FOUND));
  }


  private Card getCardById(Long cardId) {
    Card card = cardRepository.findById(cardId)
        .orElseThrow(() -> new CustomException(ErrorCode.CARD_NOT_FOUND));

    return card;
  }

}
