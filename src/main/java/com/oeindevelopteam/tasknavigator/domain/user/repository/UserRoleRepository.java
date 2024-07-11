package com.oeindevelopteam.tasknavigator.domain.user.repository;

import com.oeindevelopteam.tasknavigator.domain.user.entity.UserRole;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

  Optional<UserRole> findByRole(String role);

}
