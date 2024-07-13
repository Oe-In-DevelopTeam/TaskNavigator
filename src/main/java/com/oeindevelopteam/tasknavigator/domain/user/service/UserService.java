package com.oeindevelopteam.tasknavigator.domain.user.service;

import com.oeindevelopteam.tasknavigator.domain.user.dto.UserSignupRequestDto;
import com.oeindevelopteam.tasknavigator.domain.user.entity.User;
import com.oeindevelopteam.tasknavigator.domain.user.entity.UserRole;
import com.oeindevelopteam.tasknavigator.domain.user.repository.UserRepository;
import com.oeindevelopteam.tasknavigator.domain.user.repository.UserRoleMatchesRepository;
import com.oeindevelopteam.tasknavigator.domain.user.repository.UserRoleRepository;
import com.oeindevelopteam.tasknavigator.domain.user.security.UserDetailsImpl;
import com.oeindevelopteam.tasknavigator.global.exception.CustomException;
import com.oeindevelopteam.tasknavigator.global.exception.ErrorCode;
import com.oeindevelopteam.tasknavigator.global.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final UserRoleRepository userRoleRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserRoleMatchesRepository userRoleMatchesRepository;
  private final JwtProvider jwtProvider;

  @Value("${admin.token}")
  private String adminToken;

  public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository,
      PasswordEncoder passwordEncoder, UserRoleMatchesRepository userRoleMatchesRepository,
      JwtProvider jwtProvider) {
    this.userRepository = userRepository;
    this.userRoleRepository = userRoleRepository;
    this.passwordEncoder = passwordEncoder;
    this.userRoleMatchesRepository = userRoleMatchesRepository;
    this.jwtProvider = jwtProvider;
  }

  @Transactional
  public void signup(UserSignupRequestDto requestDto) {

    userRepository.findByUserId(requestDto.getUserId()).ifPresent((val) -> {
      throw new CustomException(ErrorCode.ALREADY_EXIST_USER);
    });

    String roleName =
        (requestDto.getAdminToken() != null && requestDto.getAdminToken().equals(adminToken))
            ? "MANAGER" : "USER";

    if ("MANAGER".equals(roleName) && userRepository.findManager().isPresent()) {
      throw new CustomException(ErrorCode.ALREADY_EXIST_MANAGER);
    }

    UserRole userRole = userRoleRepository.findByRole(roleName)
        .orElseThrow(() -> new CustomException(ErrorCode.ROLE_NOT_FOUND));

    User user = new User(requestDto, userRole);

    String encryptionPassword = passwordEncoder.encode(requestDto.getPassword());
    user.encrytionPassword(encryptionPassword);

    userRepository.save(user);

  }

  @Transactional
  public void logout() {
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    User user = userRepository.findByUserIdWithRoles(userDetails.getUsername())
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

    user.updateRefreshToken(null);
    userRepository.save(user);
  }

  public HttpHeaders refreshToken(HttpServletRequest request) {
    String token = getRefreshToken(request);

    if (!StringUtils.hasText(token)) {
      throw new CustomException(ErrorCode.BAD_REQUEST);
    }

    return validateToken(token);
  }

  private String getRefreshToken(HttpServletRequest request) {

    Cookie[] cookies = request.getCookies();

    if (cookies == null) {
      throw new CustomException(ErrorCode.BAD_REQUEST);
    }

    for (Cookie el : cookies) {
      if ("refreshToken".equals(el.getName())) {
        return el.getValue();
      }
    }

    return null;
  }

  private HttpHeaders validateToken(String token) {

    try {

      Claims info = jwtProvider.getClaimsFromToken(token);
      User user = userRepository.findByUserId(info.getSubject())
          .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

      if (!user.getRefreshToken().equals(token)) {
        throw new CustomException(ErrorCode.INVALID_TOKEN);
      }

      return setHeaders(info, user);

    } catch (ExpiredJwtException e) {
      throw new CustomException(ErrorCode.EXPIRED_TOKEN);
    } catch (JwtException e) {
      throw new CustomException(ErrorCode.INVALID_TOKEN);
    }

  }

  private HttpHeaders setHeaders(Claims info, User user) {

    String accessToken = jwtProvider.createAccessToken(user.getUserId(), user.getRole());
    String refreshToken = jwtProvider.generateToken(user.getUserId(), user.getRole(),
        info.getExpiration());
    ResponseCookie responseCookie = jwtProvider.createCookieRefreshToken(refreshToken,
        info.getExpiration());

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + accessToken);
    headers.add(HttpHeaders.SET_COOKIE, responseCookie.toString());

    user.updateRefreshToken(refreshToken);

    return headers;

  }

}
