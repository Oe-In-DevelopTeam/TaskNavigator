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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final UserRoleRepository userRoleRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserRoleMatchesRepository userRoleMatchesRepository;

  @Value("${admin.token}")
  private String adminToken;

  public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository,
      PasswordEncoder passwordEncoder, UserRoleMatchesRepository userRoleMatchesRepository) {
    this.userRepository = userRepository;
    this.userRoleRepository = userRoleRepository;
    this.passwordEncoder = passwordEncoder;
    this.userRoleMatchesRepository = userRoleMatchesRepository;
  }

  @Transactional
  public void signup(UserSignupRequestDto requestDto) {

    userRepository.findByUserId(requestDto.getUserId()).ifPresent((val) -> {
      throw new CustomException(ErrorCode.ALREADY_EXIST_USER);
    });

    String roleName;
    if (requestDto.getAdminToken() != null && requestDto.getAdminToken().equals(adminToken)) {
      roleName = "MANAGER";
    } else {
      roleName = "USER";
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
