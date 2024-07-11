package com.oeindevelopteam.tasknavigator.domain.user.controller;

import com.oeindevelopteam.tasknavigator.domain.user.dto.UserSignupRequestDto;
import com.oeindevelopteam.tasknavigator.domain.user.service.UserService;
import com.oeindevelopteam.tasknavigator.global.dto.CommonResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/signup")
  public ResponseEntity<CommonResponseDto> signup(
      @Valid @RequestBody UserSignupRequestDto requestDto) {
    userService.signup(requestDto);
    CommonResponseDto responseDto = new CommonResponseDto<>(201, "회원가입에 성공했습니다.", null);

    return ResponseEntity.status(HttpStatus.OK).body(responseDto);
  }

  @PostMapping("/logout")
  public ResponseEntity<CommonResponseDto> logout() {
    userService.logout();

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

}
