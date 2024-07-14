package com.oeindevelopteam.tasknavigator.domain.board.repository;

import com.oeindevelopteam.tasknavigator.domain.board.entity.UserBoardMatches;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserBoardMatchesRepository extends JpaRepository<UserBoardMatches, Long>,
    UserBoardMatchesRepositoryCustom {

  Optional<UserBoardMatches> findByBoardIdAndUserId(Long boardId, Long invitedUser);

  Optional<List<UserBoardMatches>> findByUserId(Long userId);

}
