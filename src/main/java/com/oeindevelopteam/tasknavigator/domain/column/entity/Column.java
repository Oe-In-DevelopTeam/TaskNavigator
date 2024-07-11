package com.oeindevelopteam.tasknavigator.domain.column.entity;

import com.oeindevelopteam.tasknavigator.domain.board.entity.Board;
import com.oeindevelopteam.tasknavigator.domain.card.entity.Card;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "'column'")
public class Column {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "column")
    private List<Card> cards = new ArrayList<>();

}