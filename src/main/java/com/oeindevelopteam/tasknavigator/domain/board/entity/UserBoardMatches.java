package com.oeindevelopteam.tasknavigator.domain.board.entity;

import com.oeindevelopteam.tasknavigator.domain.user.entity.User;
import com.oeindevelopteam.tasknavigator.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_board_matches")
@NoArgsConstructor
public class UserBoardMatches extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    public UserBoardMatches(User user, Board board) {
        this.user = user;
        this.board = board;
    }
}
