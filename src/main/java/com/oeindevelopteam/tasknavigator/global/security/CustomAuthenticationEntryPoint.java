package com.oeindevelopteam.tasknavigator.global.security;

import com.oeindevelopteam.tasknavigator.global.dto.SecurityResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final SecurityResponse securityResponse;

  public CustomAuthenticationEntryPoint(SecurityResponse securityResponse) {
    this.securityResponse = securityResponse;
  }

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
    securityResponse.sendResponse(response, HttpStatus.UNAUTHORIZED, "로그인 후 이용해 주세요.");
  }

}