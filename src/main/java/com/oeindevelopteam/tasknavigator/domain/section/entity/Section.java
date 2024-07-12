package com.oeindevelopteam.tasknavigator.domain.section.entity;

import com.oeindevelopteam.tasknavigator.domain.board.entity.Board;
import com.oeindevelopteam.tasknavigator.domain.card.entity.Card;
import com.oeindevelopteam.tasknavigator.domain.section.dto.SectionRequestDto;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "sections")
@NoArgsConstructor
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int sectionOrder;

    private String status;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "section")
    private List<Card> cards = new ArrayList<>();

    public Section(Board board, SectionRequestDto requestDto) {
        this.board = board;
        this.sectionOrder = requestDto.getSectionOrder();
        this.status = requestDto.getStatus();
    }
}