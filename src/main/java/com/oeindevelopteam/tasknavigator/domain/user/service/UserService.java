package com.oeindevelopteam.tasknavigator.domain.user.service;

import com.oeindevelopteam.tasknavigator.domain.user.dto.UserSignupRequestDto;
import com.oeindevelopteam.tasknavigator.domain.user.entity.User;
import com.oeindevelopteam.tasknavigator.domain.user.entity.UserRole;
import com.oeindevelopteam.tasknavigator.domain.user.repository.UserRepository;
import com.oeindevelopteam.tasknavigator.domain.user.repository.UserRoleRepository;
import com.oeindevelopteam.tasknavigator.domain.user.security.UserDetailsImpl;
import com.oeindevelopteam.tasknavigator.global.exception.CustomException;
import com.oeindevelopteam.tasknavigator.global.exception.ErrorCode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final UserRoleRepository userRoleRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.userRoleRepository = userRoleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public void signup(UserSignupRequestDto requestDto) {

    userRepository.findByUserId(requestDto.getUserId()).ifPresent((val) -> {
      throw new CustomException(ErrorCode.BAD_REQUEST);
    });

    // TODO : role 부여 설정
    boolean isFirstUser = userRepository.count() == 0;
    String roleName = isFirstUser ? "MANAGER" : "USER";

    UserRole userRole = userRoleRepository.findByRole(roleName)
        .orElseThrow(() -> new CustomException(ErrorCode.ROLE_NOT_FOUND));

    User user = new User(requestDto, userRole);

    String encryptionPassword = passwordEncoder.encode(requestDto.getPassword());
    user.encrytionPassword(encryptionPassword);

    userRepository.save(user);
  }

  @Transactional
  public void logout() {

    User user = getUser();
    user.updateRefreshToken(null);

  }

  public User getUser() {
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    return userRepository.findByUserId(userDetails.getUsername())
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
  }

}
