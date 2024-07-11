package com.oeindevelopteam.tasknavigator.domain.board.service;

import com.oeindevelopteam.tasknavigator.domain.board.entity.Board;
import com.oeindevelopteam.tasknavigator.domain.board.repository.UserBoardMatchesRepository;
import com.oeindevelopteam.tasknavigator.domain.user.entity.User;
import com.oeindevelopteam.tasknavigator.domain.user.entity.UserRole;
import com.oeindevelopteam.tasknavigator.domain.user.repository.UserRoleMatchesRepository;
import com.oeindevelopteam.tasknavigator.global.dto.CommonResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final UserBoardMatchesRepository userBoardMatchesRepository;
    private final UserRoleMatchesRepository userRoleMatchesRepository;

    public BoardService(UserBoardMatchesRepository userBoardMatchesRepository
                        , UserRoleMatchesRepository userRoleMatchesRepository) {

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

}
