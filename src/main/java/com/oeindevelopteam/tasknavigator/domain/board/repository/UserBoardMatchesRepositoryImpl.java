package com.oeindevelopteam.tasknavigator.domain.board.repository;

import com.oeindevelopteam.tasknavigator.domain.board.dto.BoardListResponseDto;
import com.oeindevelopteam.tasknavigator.domain.board.dto.CardDto;
import com.oeindevelopteam.tasknavigator.domain.board.dto.SectionDto;
import com.oeindevelopteam.tasknavigator.domain.board.entity.Board;
import com.oeindevelopteam.tasknavigator.domain.board.entity.QBoard;
import com.oeindevelopteam.tasknavigator.domain.board.entity.QUserBoardMatches;
import com.oeindevelopteam.tasknavigator.domain.card.entity.Card;
import com.oeindevelopteam.tasknavigator.domain.card.entity.QCard;
import com.oeindevelopteam.tasknavigator.domain.section.entity.QSection;
import com.oeindevelopteam.tasknavigator.domain.section.entity.Section;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserBoardMatchesRepositoryImpl implements UserBoardMatchesRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public List<BoardListResponseDto> findBoardByUserId(Long userId){

        QUserBoardMatches userBoardMatches = QUserBoardMatches.userBoardMatches;
        QBoard board = QBoard.board;
        QSection section = QSection.section;
        QCard card = QCard.card;

//        return jpaQueryFactory.select(board)
//                .from(userBoardMatches)
//                .innerJoin(userBoardMatches.board,board).fetchJoin()
//                .leftJoin(board.sectionList, section).fetchJoin()
//                .leftJoin(section.cards, card).fetchJoin()
//                .where(userBoardMatches.user.id.eq(userId))
//                .distinct()
//                .fetch();

        // 보드를 먼저 로드
        List<Board> boards = jpaQueryFactory.select(board)
                .from(userBoardMatches)
                .innerJoin(userBoardMatches.board,board)
                .where(userBoardMatches.user.id.eq(userId))
                .fetch();

        // 각 보드에 대해 섹션과 카드를 별도로 로드
        for (Board b : boards) {
            List<Section> sections = jpaQueryFactory.selectFrom(section)
                    .where(section.board.eq(b))
                    .orderBy(section.sectionOrder.asc())  // 섹션의 순서를 보장
                    .fetch();
            b.addSectionList(sections);

            for (Section s : sections) {
                List<Card> cards = jpaQueryFactory.selectFrom(card)
                        .where(card.section.eq(s))
                        .orderBy(card.id.asc())  // 카드의 순서를 보장
                        .fetch();
                s.addCards(cards);
            }
        }

        return boards.stream()
                .map(this::convertToBoardListResponseDto)
                .collect(Collectors.toList());
    }

    public List<BoardListResponseDto> findBoard(){

        QUserBoardMatches userBoardMatches = QUserBoardMatches.userBoardMatches;
        QBoard board = QBoard.board;
        QSection section = QSection.section;
        QCard card = QCard.card;

//        return jpaQueryFactory.select(board)
//                .from(board)
//                .leftJoin(board.sectionList, section).fetchJoin()
//                .leftJoin(section.cards, card).fetchJoin()
//                .distinct()
//                .fetch();

        // 보드를 먼저 로드
        List<Board> boards = jpaQueryFactory.selectFrom(board)
                .fetch();

        // 각 보드에 대해 섹션과 카드를 별도로 로드
        for (Board b : boards) {
            List<Section> sections = jpaQueryFactory.selectFrom(section)
                    .where(section.board.eq(b))
                    .orderBy(section.sectionOrder.asc())  // 섹션의 순서를 보장
                    .fetch();
            b.addSectionList(sections);

            for (Section s : sections) {
                List<Card> cards = jpaQueryFactory.selectFrom(card)
                        .where(card.section.eq(s))
                        .orderBy(card.id.asc())  // 카드의 순서를 보장
                        .fetch();
                s.addCards(cards);
            }
        }

        return boards.stream()
                .map(this::convertToBoardListResponseDto)
                .collect(Collectors.toList());
    }

    private BoardListResponseDto convertToBoardListResponseDto(Board board) {
        List<SectionDto> sectionDtos = board.getSectionList().stream()
                .map(this::convertToSectionDto)
                .collect(Collectors.toList());

        return new BoardListResponseDto(board.getId(), board.getBoardName(), board.getInfo(), sectionDtos);
    }

    private SectionDto convertToSectionDto(Section section) {
        List<CardDto> cardDtos = section.getCards().stream()
                .map(this::convertToCardDto)
                .collect(Collectors.toList());

        return new SectionDto(section.getId(), section.getSectionOrder(), section.getStatus(), cardDtos);
    }

    private CardDto convertToCardDto(Card card) {
        return new CardDto(card.getId(), card.getTitle(), card.getContent(), card.getDueDate(), card.getManager());
    }

}
