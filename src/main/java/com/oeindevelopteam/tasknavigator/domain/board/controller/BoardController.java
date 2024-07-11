package com.oeindevelopteam.tasknavigator.domain.board.controller;

import com.oeindevelopteam.tasknavigator.domain.board.entity.Board;
import com.oeindevelopteam.tasknavigator.domain.board.service.BoardService;
import com.oeindevelopteam.tasknavigator.domain.user.security.UserDetailsImpl;
import com.oeindevelopteam.tasknavigator.global.dto.CommonResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/boards")
    public ResponseEntity<CommonResponseDto<List<Board>>> getAllBoards(@AuthenticationPrincipal UserDetailsImpl userDetails){

        CommonResponseDto<List<Board>> boardList = boardService.getAllBoards(userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(boardList);

    }

}
