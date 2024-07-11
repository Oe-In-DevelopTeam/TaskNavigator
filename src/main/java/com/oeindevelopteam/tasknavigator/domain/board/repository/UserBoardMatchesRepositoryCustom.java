package com.oeindevelopteam.tasknavigator.domain.board.repository;

import com.oeindevelopteam.tasknavigator.domain.board.entity.Board;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBoardMatchesRepositoryCustom {

    List<Board> findBoardByUserId(Long userId);
    List<Board> findBoard();

}
