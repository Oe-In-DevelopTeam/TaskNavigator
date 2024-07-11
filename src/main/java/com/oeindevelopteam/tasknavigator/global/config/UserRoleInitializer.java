package com.oeindevelopteam.tasknavigator.global.config;

import com.oeindevelopteam.tasknavigator.domain.user.entity.UserRole;
import com.oeindevelopteam.tasknavigator.domain.user.repository.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class UserRoleInitializer {

  @Bean
  public CommandLineRunner initUserRole(UserRoleRepository userRoleRepository) {
    return args -> {
      addRoleIfNotExists(userRoleRepository, "USER");
      addRoleIfNotExists(userRoleRepository, "MANAGER");
    };
  }

  @Transactional
  public void addRoleIfNotExists(UserRoleRepository userRoleRepository, String roleName) {
    userRoleRepository.findByRole(roleName).orElseGet(() -> {
      UserRole role = new UserRole();
      role.setRole(roleName);
      return userRoleRepository.save(role);
    });
  }

}
