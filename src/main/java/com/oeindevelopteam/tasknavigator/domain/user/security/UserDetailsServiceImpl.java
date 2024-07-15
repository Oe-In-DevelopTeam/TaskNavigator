package com.oeindevelopteam.tasknavigator.domain.user.security;

import com.oeindevelopteam.tasknavigator.domain.user.entity.User;
import com.oeindevelopteam.tasknavigator.domain.user.repository.UserRepository;
import com.oeindevelopteam.tasknavigator.global.exception.CustomException;
import com.oeindevelopteam.tasknavigator.global.exception.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;
  private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

    User user = userRepository.findByUserIdWithRoles(userId).orElseThrow(() -> new CustomException(
        ErrorCode.USER_NOT_FOUND));

    return new UserDetailsImpl(user);
  }

}