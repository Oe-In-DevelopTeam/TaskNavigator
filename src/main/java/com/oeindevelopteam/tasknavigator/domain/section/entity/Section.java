package com.oeindevelopteam.tasknavigator.domain.section.entity;

import com.oeindevelopteam.tasknavigator.domain.board.entity.Board;
import com.oeindevelopteam.tasknavigator.domain.card.entity.Card;
import com.oeindevelopteam.tasknavigator.domain.section.dto.SectionRequestDto;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "컬럼 순서는 필수입니다.")
    private int sectionOrder;

    @NotNull(message = "컬럼 상태는 필수입니다.")
    private String status;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards = new ArrayList<>();

    public Section(Board board, SectionRequestDto requestDto) {
        this.board = board;
        this.sectionOrder = requestDto.getSectionOrder();
        this.status = requestDto.getStatus();
    }

    public void updateOrder(int order) {
        this.sectionOrder = order;
    }

    public void addCards(List<Card> cards) {
        this.cards = cards;
    }
}