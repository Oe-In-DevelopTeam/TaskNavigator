package com.oeindevelopteam.tasknavigator.domain.board.entity;

import com.oeindevelopteam.tasknavigator.domain.board.dto.BoardRequestDto;
import com.oeindevelopteam.tasknavigator.domain.section.entity.Section;
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

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserBoardMatches> userBoardMatchesList = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Section> sectionList = new ArrayList<>();

    public Board(String boardName, String info) {

        this.boardName = boardName;
        this.info = info;

    }

    public Board updateBoard(BoardRequestDto boardRequestDto){

        this.boardName = boardRequestDto.getBoardName();
        this.info = boardRequestDto.getInfo();

        return this;

    }

    public void addSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }

}
