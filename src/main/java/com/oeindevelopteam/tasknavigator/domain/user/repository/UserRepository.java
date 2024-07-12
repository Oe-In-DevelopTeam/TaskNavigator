package com.oeindevelopteam.tasknavigator.domain.user.repository;

import com.oeindevelopteam.tasknavigator.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUserId(String userId);

}
