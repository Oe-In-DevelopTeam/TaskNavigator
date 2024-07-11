package com.oeindevelopteam.tasknavigator.domain.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = -1049254539L;

    public static final QBoard board = new QBoard("board");

    public final com.oeindevelopteam.tasknavigator.global.entity.QTimestamped _super = new com.oeindevelopteam.tasknavigator.global.entity.QTimestamped(this);

    public final StringPath boardName = createString("boardName");

    public final ListPath<com.oeindevelopteam.tasknavigator.domain.column.entity.Column, com.oeindevelopteam.tasknavigator.domain.column.entity.QColumn> columnList = this.<com.oeindevelopteam.tasknavigator.domain.column.entity.Column, com.oeindevelopteam.tasknavigator.domain.column.entity.QColumn>createList("columnList", com.oeindevelopteam.tasknavigator.domain.column.entity.Column.class, com.oeindevelopteam.tasknavigator.domain.column.entity.QColumn.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath info = createString("info");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final ListPath<UserBoardMatches, QUserBoardMatches> userBoardMatchesList = this.<UserBoardMatches, QUserBoardMatches>createList("userBoardMatchesList", UserBoardMatches.class, QUserBoardMatches.class, PathInits.DIRECT2);

    public QBoard(String variable) {
        super(Board.class, forVariable(variable));
    }

    public QBoard(Path<? extends Board> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoard(PathMetadata metadata) {
        super(Board.class, metadata);
    }

}

