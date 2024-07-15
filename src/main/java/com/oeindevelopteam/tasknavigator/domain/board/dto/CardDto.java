package com.oeindevelopteam.tasknavigator.domain.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CardDto {

    private Long cardId;
    private String title;
    private String content;
    private String dueDate;
    private String manager;

    public CardDto(Long cardId, String title, String content, String dueDate, String manager) {
        this.cardId = cardId;
        this.title = title;
        this.content = content;
        this.dueDate = dueDate;
        this.manager = manager;
    }

}
