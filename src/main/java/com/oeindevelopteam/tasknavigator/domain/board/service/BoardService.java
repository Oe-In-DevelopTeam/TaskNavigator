package com.oeindevelopteam.tasknavigator.domain.board.service;

import com.oeindevelopteam.tasknavigator.domain.board.dto.BoardListResponseDto;
import com.oeindevelopteam.tasknavigator.domain.board.dto.BoardRequestDto;
import com.oeindevelopteam.tasknavigator.domain.board.dto.BoardResponseDto;
import com.oeindevelopteam.tasknavigator.domain.board.entity.Board;
import com.oeindevelopteam.tasknavigator.domain.board.entity.UserBoardMatches;
import com.oeindevelopteam.tasknavigator.domain.board.repository.BoardRepository;
import com.oeindevelopteam.tasknavigator.domain.board.repository.UserBoardMatchesRepository;
import com.oeindevelopteam.tasknavigator.domain.user.entity.User;
import com.oeindevelopteam.tasknavigator.domain.user.entity.UserRole;
import com.oeindevelopteam.tasknavigator.domain.user.entity.UserRoleMatches;
import com.oeindevelopteam.tasknavigator.domain.user.repository.UserRepository;
import com.oeindevelopteam.tasknavigator.domain.user.repository.UserRoleMatchesRepository;
import com.oeindevelopteam.tasknavigator.global.exception.CustomException;
import com.oeindevelopteam.tasknavigator.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final UserBoardMatchesRepository userBoardMatchesRepository;
    private final UserRoleMatchesRepository userRoleMatchesRepository;

    public BoardService(UserBoardMatchesRepository userBoardMatchesRepository
                        , UserRepository userRepository
                        , UserRoleMatchesRepository userRoleMatchesRepository
                        , BoardRepository boardRepository) {

        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
        this.userBoardMatchesRepository = userBoardMatchesRepository;
        this.userRoleMatchesRepository = userRoleMatchesRepository;

    }

    public List<BoardListResponseDto> getAllBoards(User user) {

        // userId 로 Board 권한 체크
        List<UserRoleMatches> roles = userRoleMatchesRepository.findByUser(user);

        Boolean findAll = false;

        for (UserRoleMatches l : roles){
            if (l.getUserRole().getRole().equals("MANAGER")){
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
        List<UserRoleMatches> roles = userRoleMatchesRepository.findByUser(user);

        Boolean findAll = false;

        for (UserRoleMatches l : roles){
            if (l.getUserRole().getRole().equals("MANAGER")){
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
        List<UserRoleMatches> roles = userRoleMatchesRepository.findByUser(user);

        Boolean findAll = false;

        for (UserRoleMatches l : roles){
            if (l.getUserRole().getRole().equals("MANAGER")){
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

    public void deleteBoard(User user, Long boardId) {

        // userId 로 Board 권한 체크
        List<UserRoleMatches> roles = userRoleMatchesRepository.findByUser(user);

        Boolean findAll = false;

        for (UserRoleMatches l : roles){
            if (l.getUserRole().getRole().equals("MANAGER")){
                findAll = true;
                break;
            }
        }

        if (!findAll){
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        boardRepository.delete(board);

    }


    @Transactional
    public BoardResponseDto inviteBoard(User user, Long boardId, Long invitedUserId) {

        // userId 로 Board 권한 체크
        List<UserRoleMatches> roles = userRoleMatchesRepository.findByUser(user);

        Boolean findAll = false;

        for (UserRoleMatches l : roles){
            if (l.getUserRole().getRole().equals("MANAGER")){
                findAll = true;
                break;
            }
        }

        // 자신을 초대할 수 없다.
        if(user.getId().equals(invitedUserId)){
            throw new CustomException(ErrorCode.INVITATION_NOT_ALLOWED);
        }

        // invitedUserId 로 Role 권한 체크
        User invitedUser = userRepository.findById(invitedUserId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        roles = userRoleMatchesRepository.findByUser(invitedUser);

        Boolean isManager = false;

        for (UserRoleMatches l : roles){
            if (l.getUserRole().getRole().equals("MANAGER")){
                isManager = true;
                break;
            }
        }

        Optional<UserBoardMatches> userBoardMatches = userBoardMatchesRepository.findByBoardIdAndUserId(boardId, invitedUserId);

        // 보드 초대가 이미 된 상태의 유저이거나, 매니저를 초대 했을 경우
        if (userBoardMatches.isPresent() || isManager) {

            throw new CustomException(ErrorCode.ALREADY_HAS_ACCESS);

        } else {

            User invitedUserEntity = userRepository.findById(invitedUserId)
                    .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

            Board board = boardRepository.findById(boardId)
                    .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

            UserBoardMatches newUserBoardMatch = new UserBoardMatches(invitedUserEntity, board);
            userBoardMatchesRepository.save(newUserBoardMatch);

            // 변경된 보드 정보를 반환
            return new BoardResponseDto(board.getBoardName(), board.getInfo());
        }

    }

    public Board getBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() ->
            new CustomException(ErrorCode.BOARD_NOT_FOUND)
        );
    }
}