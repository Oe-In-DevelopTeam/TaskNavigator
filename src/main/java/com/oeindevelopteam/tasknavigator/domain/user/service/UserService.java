package com.oeindevelopteam.tasknavigator.domain.user.service;

import com.oeindevelopteam.tasknavigator.domain.user.dto.UserSignupRequestDto;
import com.oeindevelopteam.tasknavigator.domain.user.entity.User;
import com.oeindevelopteam.tasknavigator.domain.user.entity.UserRole;
import com.oeindevelopteam.tasknavigator.domain.user.repository.UserRepository;
import com.oeindevelopteam.tasknavigator.domain.user.repository.UserRoleRepository;
import com.oeindevelopteam.tasknavigator.global.exception.CustomException;
import com.oeindevelopteam.tasknavigator.global.exception.ErrorCode;
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
      throw new CustomException(ErrorCode.FAIL);
    });

    UserRole userRole = userRoleRepository.findByRole("USER")
        .orElseThrow(() -> new CustomException(ErrorCode.FAIL));

    User user = new User(requestDto, userRole);

    String encryptionPassword = passwordEncoder.encode(requestDto.getPassword());
    user.encrytionPassword(encryptionPassword);

    userRepository.save(user);
  }

}
