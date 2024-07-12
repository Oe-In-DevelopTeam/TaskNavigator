package com.oeindevelopteam.tasknavigator.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
  FAIL(500, "실패했습니다."),
  CARD_NOT_FOUND(404, "해당 카드를 찾을 수 없습니다."),
  CARD_TAG_NOT_FOUND(404, "해당 카드 태그를 찾을 수 없습니다.");
  BAD_REQUEST(400, "잘못된 요청입니다."),
  UNAUTHORIZED(401, "접근 권한이 없습니다."),
  CARD_NOT_FOUND(404, "해당 카드를 찾을 수 없습니다."),
  USER_NOT_FOUND(404, "해당 유저를 찾을 수 없습니다."),
  ROLE_NOT_FOUND(404, "해당 역할을 찾을 수 없습니다."),
  BOARD_NOT_FOUND(404, "해당 보드를 찾을 수 없습니다."),
  REQUIRE_BOARD_NAME(404, "보드 이름을 입력해주세요."),
  REQUIRE_BOARD_INFO(404, "한줄 소개를 입력해주세요.");

  private int status;
  private String message;
}