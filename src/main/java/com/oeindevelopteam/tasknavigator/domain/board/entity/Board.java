package com.oeindevelopteam.tasknavigator.domain.board.entity;

import com.oeindevelopteam.tasknavigator.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "board")
@NoArgsConstructor
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String boardName;

    @Column(nullable = false)
    private String info;

    @OneToMany(mappedBy = "board")
    private List<UserBoardMatches> userBoardMatchesList = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<com.oeindevelopteam.tasknavigator.domain.column.entity.Column> columnList = new ArrayList<>();

    public Board(String boardName, String info) {

        this.boardName = boardName;
        this.info = info;

    }

}
