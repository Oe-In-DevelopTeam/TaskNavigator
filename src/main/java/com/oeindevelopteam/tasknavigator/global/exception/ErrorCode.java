package com.oeindevelopteam.tasknavigator.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
  FAIL(500, "실패했습니다."),
  CARD_NOT_FOUND(404, "해당 카드를 찾을 수 없습니다."),
  CARD_TAG_NOT_FOUND(404, "해당 카드 태그를 찾을 수 없습니다.");

  private int status;
  private String message;
}