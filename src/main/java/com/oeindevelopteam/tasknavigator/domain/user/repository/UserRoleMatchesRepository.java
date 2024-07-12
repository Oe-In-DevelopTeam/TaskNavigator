package com.oeindevelopteam.tasknavigator.domain.user.repository;

import com.oeindevelopteam.tasknavigator.domain.user.entity.User;
import com.oeindevelopteam.tasknavigator.domain.user.entity.UserRoleMatches;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleMatchesRepository extends JpaRepository<UserRoleMatches, Long> {

    List<UserRoleMatches> findByUser(User user);

}
