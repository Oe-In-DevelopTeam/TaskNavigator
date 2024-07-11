package com.oeindevelopteam.tasknavigator.domain.board.repository;

import com.oeindevelopteam.tasknavigator.domain.board.dto.BoardResponseDto;
import com.oeindevelopteam.tasknavigator.domain.board.entity.UserBoardMatches;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBoardMatchesRepository extends JpaRepository<UserBoardMatches, Long>, UserBoardMatchesRepositoryCustom {
}
