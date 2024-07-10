package com.oeindevelopteam.tasknavigator.domain.card.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CardRequestDto {

  @NotBlank(message = "카드 제목을 작성해주세요!")
  private String title;
  private String content;
  private String dueDate;
  private String manager;
}
