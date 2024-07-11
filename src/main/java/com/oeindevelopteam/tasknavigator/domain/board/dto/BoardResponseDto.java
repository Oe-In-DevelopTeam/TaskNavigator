package com.oeindevelopteam.tasknavigator.domain.board.dto;

import lombok.Getter;

@Getter
public class BoardResponseDto {

    private String boardName;
    private String info;

    public BoardResponseDto(String boardName, String info) {
        this.boardName = boardName;
        this.info = info;
    }
}
