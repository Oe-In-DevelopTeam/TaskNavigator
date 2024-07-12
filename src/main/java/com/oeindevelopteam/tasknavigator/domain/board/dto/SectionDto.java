package com.oeindevelopteam.tasknavigator.domain.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class SectionDto {

    private int sectionOrder;
    private String status;
    private List<CardDto> cards; // CardDto 리스트 필드 추가

    public SectionDto(int sectionOrder, String status, List<CardDto> cards) {
        this.sectionOrder = sectionOrder;
        this.status = status;
        this.cards = cards;
    }

}
