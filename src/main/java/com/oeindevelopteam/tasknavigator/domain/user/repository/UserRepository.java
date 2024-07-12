package com.oeindevelopteam.tasknavigator.domain.user.repository;

import com.oeindevelopteam.tasknavigator.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUserId(String userId);

  @Query("SELECT u FROM User u JOIN FETCH u.userRoleMatches WHERE u.userId = :userId")
  Optional<User> findByUserIdWithRoles(@Param("userId") String userId);

  @Query("SELECT u FROM User u JOIN u.userRoleMatches urm JOIN urm.userRole ur WHERE ur.role = 'MANAGER'")
  Optional<User> findManager();

}
