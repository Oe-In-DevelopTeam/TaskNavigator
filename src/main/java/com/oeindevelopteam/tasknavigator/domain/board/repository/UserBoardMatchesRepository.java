package com.oeindevelopteam.tasknavigator.domain.board.repository;

import com.oeindevelopteam.tasknavigator.domain.board.entity.UserBoardMatches;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBoardMatchesRepository extends JpaRepository<UserBoardMatches, Long>, UserBoardMatchesRepositoryCustom {
}
