package com.oeindevelopteam.tasknavigator.domain.board.repository;

import com.oeindevelopteam.tasknavigator.domain.board.entity.Board;
import com.oeindevelopteam.tasknavigator.domain.board.entity.QBoard;
import com.oeindevelopteam.tasknavigator.domain.board.entity.QUserBoardMatches;
import com.oeindevelopteam.tasknavigator.domain.card.entity.QCard;
import com.oeindevelopteam.tasknavigator.domain.section.entity.QSection;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserBoardMatchesRepositoryImpl implements UserBoardMatchesRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public List<Board> findBoardByUserId(Long userId){

        QUserBoardMatches userBoardMatches = QUserBoardMatches.userBoardMatches;
        QBoard board = QBoard.board;
        QSection section = QSection.section;
        QCard card = QCard.card;

        return jpaQueryFactory.select(board)
                .from(userBoardMatches)
                .innerJoin(userBoardMatches.board,board).fetchJoin()
                .leftJoin(board.sectionList, section).fetchJoin()
                .leftJoin(section.cards, card).fetchJoin()
                .where(userBoardMatches.user.id.eq(userId))
                .fetch();

    }

    public List<Board> findBoard(){

        QUserBoardMatches userBoardMatches = QUserBoardMatches.userBoardMatches;
        QBoard board = QBoard.board;
        QSection section = QSection.section;
        QCard card = QCard.card;

        return jpaQueryFactory.select(board)
                .from(userBoardMatches)
                .innerJoin(userBoardMatches.board,board).fetchJoin()
                .leftJoin(board.sectionList, section).fetchJoin()
                .leftJoin(section.cards, card).fetchJoin()
                .fetch();

    }
}
