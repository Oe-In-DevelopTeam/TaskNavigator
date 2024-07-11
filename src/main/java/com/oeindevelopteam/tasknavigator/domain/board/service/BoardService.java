package com.oeindevelopteam.tasknavigator.domain.board.service;

import com.oeindevelopteam.tasknavigator.domain.board.dto.BoardRequestDto;
import com.oeindevelopteam.tasknavigator.domain.board.dto.BoardResponseDto;
import com.oeindevelopteam.tasknavigator.domain.board.entity.Board;
import com.oeindevelopteam.tasknavigator.domain.board.repository.BoardRepository;
import com.oeindevelopteam.tasknavigator.domain.board.repository.UserBoardMatchesRepository;
import com.oeindevelopteam.tasknavigator.domain.user.entity.User;
import com.oeindevelopteam.tasknavigator.domain.user.entity.UserRole;
import com.oeindevelopteam.tasknavigator.domain.user.repository.UserRoleMatchesRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

  private final BoardRepository boardRepository;
  private final UserBoardMatchesRepository userBoardMatchesRepository;
  private final UserRoleMatchesRepository userRoleMatchesRepository;

  public BoardService(UserBoardMatchesRepository userBoardMatchesRepository
      , UserRoleMatchesRepository userRoleMatchesRepository
      , BoardRepository boardRepository) {

    this.boardRepository = boardRepository;
    this.userBoardMatchesRepository = userBoardMatchesRepository;
    this.userRoleMatchesRepository = userRoleMatchesRepository;

  }

  public List<Board> getAllBoards(User user) {

    // userId 로 Board 권한 체크
    List<UserRole> roles = userRoleMatchesRepository.findUserRoleByUserId(user);

    List<Board> boardList;

    Boolean findAll = false;

    for (UserRole l : roles) {
      if (l.getRole().equals("MANAGER")) {
        findAll = true;
        break;
      }
    }

    if (findAll) {

      // 매니저 - 모든 Board 리스트 조회
      return userBoardMatchesRepository.findBoard();

    } else {

      // User - 초대받은 Board 리스트 조회
      return userBoardMatchesRepository.findBoardByUserId(user.getId());

    }

  }

  public BoardResponseDto createBoard(User user, BoardRequestDto boardRequestDto) {

    // userId 로 Board 권한 체크
    List<UserRole> roles = userRoleMatchesRepository.findUserRoleByUserId(user);

    Boolean findAll = false;

    for (UserRole l : roles) {
      if (l.getRole().equals("MANAGER")) {
        findAll = true;
        break;
      }
    }

    if (!findAll) {
      throw new AccessDeniedException("보드를 생성할 수 없습니다.");
    }

    Board board = new Board(boardRequestDto.getBoardName(), boardRequestDto.getInfo());

    boardRepository.save(board);

    Board saveBoard = boardRepository.findById(board.getId())
        .orElseThrow(() -> new NoSuchElementException("보드 조회 실패"));

    BoardResponseDto boardResponseDto = new BoardResponseDto(saveBoard.getBoardName(),
        saveBoard.getInfo());

    return boardResponseDto;

  }
}
