package com.oeindevelopteam.tasknavigator.domain.board.service;

import com.oeindevelopteam.tasknavigator.domain.board.dto.BoardRequestDto;
import com.oeindevelopteam.tasknavigator.domain.board.dto.BoardResponseDto;
import com.oeindevelopteam.tasknavigator.domain.board.entity.Board;
import com.oeindevelopteam.tasknavigator.domain.board.repository.BoardRepository;
import com.oeindevelopteam.tasknavigator.domain.board.repository.UserBoardMatchesRepository;
import com.oeindevelopteam.tasknavigator.domain.user.entity.User;
import com.oeindevelopteam.tasknavigator.domain.user.entity.UserRole;
import com.oeindevelopteam.tasknavigator.domain.user.repository.UserRoleMatchesRepository;
import com.oeindevelopteam.tasknavigator.global.dto.CommonResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.NoSuchElementException;

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
            throw new AccessDeniedException("보드를 생성할 수 없습니다. 매니저에게 문의주세요.");
        }

        if(!StringUtils.hasText(boardRequestDto.getBoardName())){
            throw new IllegalArgumentException("보드 이름을 입력해주세요.");
        } else if(!StringUtils.hasText(boardRequestDto.getInfo())){
            throw new IllegalArgumentException("한줄 설명을 입력해주세요.");
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
            throw new AccessDeniedException("보드 정보를 수정할 수 없습니다. 매니저에게 문의주세요.");
        }

        if(!StringUtils.hasText(boardRequestDto.getBoardName())){
            throw new IllegalArgumentException("보드 이름을 입력해주세요.");
        } else if(!StringUtils.hasText(boardRequestDto.getInfo())){
            throw new IllegalArgumentException("한줄 설명을 입력해주세요.");
        }

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("요청하신 보드가 존재하지 않습니다."));

        board.updateBoard(boardRequestDto);

        return new BoardResponseDto(board.getBoardName(), board.getInfo());

    }
}
