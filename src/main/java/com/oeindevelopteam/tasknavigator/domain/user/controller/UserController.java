package com.oeindevelopteam.tasknavigator.domain.user.controller;

import com.oeindevelopteam.tasknavigator.domain.user.dto.UserInfoDto;
import com.oeindevelopteam.tasknavigator.domain.user.dto.UserSignupRequestDto;
import com.oeindevelopteam.tasknavigator.domain.user.security.UserDetailsImpl;
import com.oeindevelopteam.tasknavigator.domain.user.service.UserService;
import com.oeindevelopteam.tasknavigator.global.dto.CommonResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/login-page")
  public String loginPage() {
    return "login";
  }

  @GetMapping("/signup-page")
  public String signupPage() {
    return "signup";
  }

  @ResponseBody
  @GetMapping("/user-info")
  public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    String username = userDetails.getUser().getUsername();
    String role = userDetails.getUser().getRole();
    boolean isAdmin = (role == "MANAGER");

    return new UserInfoDto(username, isAdmin);
  }

  @ResponseBody
  @PostMapping("/signup")
  public ResponseEntity<CommonResponseDto> signup(
      @Valid @RequestBody UserSignupRequestDto requestDto) {

    userService.signup(requestDto);
    CommonResponseDto responseDto = new CommonResponseDto<>(201, "회원가입에 성공했습니다.", null);

    return ResponseEntity.status(HttpStatus.OK).body(responseDto);
  }

  @ResponseBody
  @PostMapping("/logout")
  public ResponseEntity<CommonResponseDto> logout() {
    userService.logout();

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PostMapping("token/refresh")
  public ResponseEntity<CommonResponseDto> refreshToken(HttpServletRequest request) {
    HttpHeaders headers = userService.refreshToken(request);
    CommonResponseDto responseDto = new CommonResponseDto(200, "토큰 재발급에 성공했습니다.", null);

    return ResponseEntity.status(HttpStatus.OK).headers(headers).body(responseDto);
  }

}
