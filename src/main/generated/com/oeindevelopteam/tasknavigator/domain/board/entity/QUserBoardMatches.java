package com.oeindevelopteam.tasknavigator.domain.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserBoardMatches is a Querydsl query type for UserBoardMatches
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserBoardMatches extends EntityPathBase<UserBoardMatches> {

    private static final long serialVersionUID = 756389097L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserBoardMatches userBoardMatches = new QUserBoardMatches("userBoardMatches");

    public final com.oeindevelopteam.tasknavigator.global.entity.QTimestamped _super = new com.oeindevelopteam.tasknavigator.global.entity.QTimestamped(this);

    public final QBoard board;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final com.oeindevelopteam.tasknavigator.domain.user.entity.QUser user;

    public QUserBoardMatches(String variable) {
        this(UserBoardMatches.class, forVariable(variable), INITS);
    }

    public QUserBoardMatches(Path<? extends UserBoardMatches> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserBoardMatches(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserBoardMatches(PathMetadata metadata, PathInits inits) {
        this(UserBoardMatches.class, metadata, inits);
    }

    public QUserBoardMatches(Class<? extends UserBoardMatches> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board")) : null;
        this.user = inits.isInitialized("user") ? new com.oeindevelopteam.tasknavigator.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

