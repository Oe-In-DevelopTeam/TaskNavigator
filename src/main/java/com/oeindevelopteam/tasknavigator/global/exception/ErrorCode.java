package com.oeindevelopteam.tasknavigator.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
  FAIL(500, "실패했습니다."),
  CARD_TAG_NOT_FOUND(404, "해당 카드 태그를 찾을 수 없습니다."),
  BAD_REQUEST(400, "잘못된 요청입니다."),
  UNAUTHORIZED(401, "접근 권한이 없습니다."),
  CARD_NOT_FOUND(404, "해당 카드를 찾을 수 없습니다."),
  USER_NOT_FOUND(404, "해당 유저를 찾을 수 없습니다."),
  ROLE_NOT_FOUND(404, "해당 역할을 찾을 수 없습니다."),
  BOARD_NOT_FOUND(404, "해당 보드를 찾을 수 없습니다."),
  SECTION_NOT_FOUND(404, "해당 컬럼을 찾을 수 없습니다."),
  SECTION_STATUS_NOT_FOUND(404, "해당 컬럼 상태를 찾을 수 없습니다."),
  REQUIRE_BOARD_NAME(404, "보드 이름을 입력해주세요."),
  REQUIRE_BOARD_INFO(404, "한줄 소개를 입력해주세요."),
  DUPLICATE_STATUS(400, "중복된 상태입니다."),
  ALREADY_HAS_ACCESS(404, "해당 사용자는 이미 권한이 있습니다."),
  ALREADY_EXIST_USER(400, "해당 사용자는 이미 존재합니다."),
  ALREADY_EXIST_MANAGER(400, "관리자 권한을 가진 사용자가 이미 존재합니다."),
  MULTIPLE_PARAMETERS_NOT_ALLOWED(400, "여러 파라미터가 허용되지 않습니다.");
  EXPIRED_TOKEN(400, "만료된 토큰 입니다."),
  INVALID_TOKEN(400, "유효하지 않은 토큰 입니다.");

  private int status;
  private String message;
}