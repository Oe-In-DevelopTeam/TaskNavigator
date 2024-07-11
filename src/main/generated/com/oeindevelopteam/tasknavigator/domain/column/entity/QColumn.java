package com.oeindevelopteam.tasknavigator.domain.column.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QColumn is a Querydsl query type for Column
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QColumn extends EntityPathBase<Column> {

    private static final long serialVersionUID = -669712069L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QColumn column = new QColumn("column");

    public final com.oeindevelopteam.tasknavigator.domain.board.entity.QBoard board;

    public final ListPath<com.oeindevelopteam.tasknavigator.domain.card.entity.Card, com.oeindevelopteam.tasknavigator.domain.card.entity.QCard> cards = this.<com.oeindevelopteam.tasknavigator.domain.card.entity.Card, com.oeindevelopteam.tasknavigator.domain.card.entity.QCard>createList("cards", com.oeindevelopteam.tasknavigator.domain.card.entity.Card.class, com.oeindevelopteam.tasknavigator.domain.card.entity.QCard.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QColumn(String variable) {
        this(Column.class, forVariable(variable), INITS);
    }

    public QColumn(Path<? extends Column> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QColumn(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QColumn(PathMetadata metadata, PathInits inits) {
        this(Column.class, metadata, inits);
    }

    public QColumn(Class<? extends Column> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new com.oeindevelopteam.tasknavigator.domain.board.entity.QBoard(forProperty("board")) : null;
    }

}

