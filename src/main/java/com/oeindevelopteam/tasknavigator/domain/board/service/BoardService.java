package com.oeindevelopteam.tasknavigator.domain.board.service;

import com.oeindevelopteam.tasknavigator.domain.board.dto.BoardRequestDto;
import com.oeindevelopteam.tasknavigator.domain.board.dto.BoardResponseDto;
import com.oeindevelopteam.tasknavigator.domain.board.entity.Board;
import com.oeindevelopteam.tasknavigator.domain.board.repository.BoardRepository;
import com.oeindevelopteam.tasknavigator.domain.board.repository.UserBoardMatchesRepository;
import com.oeindevelopteam.tasknavigator.domain.user.entity.User;
import com.oeindevelopteam.tasknavigator.domain.user.entity.UserRole;
import com.oeindevelopteam.tasknavigator.domain.user.repository.UserRoleMatchesRepository;
import com.oeindevelopteam.tasknavigator.global.exception.CustomException;
import com.oeindevelopteam.tasknavigator.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;



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

        for (UserRole l : roles){
            if (l.getRole().equals("MANAGER")){
                findAll = true;
                break;
            }
        }

        if (findAll){

            // 매니저 - 모든 Board 리스트 조회
            return userBoardMatchesRepository.findBoard();

        } else {

            // User - 초대받은 Board 리스트 조회
            return userBoardMatchesRepository.findBoardByUserId(user.getId());

        }

    }

    @Transactional
    public BoardResponseDto createBoard(User user, BoardRequestDto boardRequestDto) {

        // userId 로 Board 권한 체크
        List<UserRole> roles = userRoleMatchesRepository.findUserRoleByUserId(user);

        Boolean findAll = false;

        for (UserRole l : roles){
            if (l.getRole().equals("MANAGER")){
                findAll = true;
                break;
            }
        }

        if (!findAll){
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        if(!StringUtils.hasText(boardRequestDto.getBoardName())){
            throw new CustomException(ErrorCode.REQUIRE_BOARD_NAME);
        } else if(!StringUtils.hasText(boardRequestDto.getInfo())){
            throw new CustomException(ErrorCode.REQUIRE_BOARD_INFO);
        }

        Board board = new Board(boardRequestDto.getBoardName(), boardRequestDto.getInfo());

        Board saveBoard = boardRepository.save(board);

        return new BoardResponseDto(saveBoard.getBoardName(), saveBoard.getInfo());

    }

    @Transactional
    public BoardResponseDto updateBoard(User user, Long boardId, BoardRequestDto boardRequestDto) {

        // userId 로 Board 권한 체크
        List<UserRole> roles = userRoleMatchesRepository.findUserRoleByUserId(user);

        Boolean findAll = false;

        for (UserRole l : roles){
            if (l.getRole().equals("MANAGER")){
                findAll = true;
                break;
            }
        }

        if (!findAll){
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        if(!StringUtils.hasText(boardRequestDto.getBoardName())){
            throw new CustomException(ErrorCode.REQUIRE_BOARD_NAME);
        } else if(!StringUtils.hasText(boardRequestDto.getInfo())){
            throw new CustomException(ErrorCode.REQUIRE_BOARD_INFO);
        }

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        board.updateBoard(boardRequestDto);

        return new BoardResponseDto(board.getBoardName(), board.getInfo());

    }
}