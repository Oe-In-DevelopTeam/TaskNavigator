package com.oeindevelopteam.tasknavigator.domain.comment.dto;

import com.oeindevelopteam.tasknavigator.domain.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {

  private Long cardId;
  private Long userId;
  private String comment;

  public CommentResponseDto(Comment comment) {
    this.cardId = comment.getCard().getId();
    this.userId = comment.getUserId();
    this.comment = comment.getComment();
  }
}
