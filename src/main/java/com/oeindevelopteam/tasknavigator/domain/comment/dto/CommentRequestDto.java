package com.oeindevelopteam.tasknavigator.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentRequestDto {

  @NotBlank(message = "댓글 내용을 작성해주세요!")
  private String comment;

}
