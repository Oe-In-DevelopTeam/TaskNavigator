package com.oeindevelopteam.tasknavigator.domain.card.dto;

import com.oeindevelopteam.tasknavigator.domain.card.entity.Card;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class CardResponseDto {

  private Long cardId;
  private Long userId;
  private Long boardId;
  private Long columnId;
  private String title;
  private String content;
  private String dueDate;
  private String manager;
  private Set<String> tagMatches;

  public CardResponseDto(Card card) {
    this.cardId = card.getId();
    this.userId = card.getUserId();
    this.boardId = card.getBoardId();
    this.columnId = card.getColumnId();
    this.title = card.getTitle();
    this.content = card.getContent();
    this.dueDate = card.getDueDate();
    this.manager = card.getManager();
    this.tagMatches = card.getTagMatches().stream()
        .map(cardTagMatch -> cardTagMatch.getTag().getName())
        .collect(Collectors.toSet());
  }
}