package com.oeindevelopteam.tasknavigator.domain.card.dto;

import com.oeindevelopteam.tasknavigator.domain.card.entity.Card;
import lombok.Getter;

@Getter
public class CardResponseDto {

  private Long cardId;
  private Long userId;
  private Long columnId;
  private String title;
  private String content;
  private String dueDate;
  private String manager;

  public CardResponseDto(Card card) {
    this.cardId = card.getId();
    this.userId = card.getUserId();
    this.columnId = card.getColumnId();
    this.title = card.getTitle();
    this.content = card.getContent();
    this.dueDate = card.getDueDate();
    this.manager = card.getManager();
  }
}
