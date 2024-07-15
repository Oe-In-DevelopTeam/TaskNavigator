package com.oeindevelopteam.tasknavigator.domain.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class BoardListResponseDto {

    private Long boardId;
    private String boardName;
    private String info;
    private List<SectionDto> sections; // SectionDto 리스트 필드 추가

    // 매개변수 생성자
    public BoardListResponseDto(Long boardId, String boardName, String info, List<SectionDto> sections) {
        this.boardId = boardId;
        this.boardName = boardName;
        this.info = info;
        this.sections = sections;
    }

}
