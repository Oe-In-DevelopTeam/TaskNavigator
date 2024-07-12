package com.oeindevelopteam.tasknavigator.domain.card.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCard is a Querydsl query type for Card
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCard extends EntityPathBase<Card> {

    private static final long serialVersionUID = -1492339857L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCard card = new QCard("card");

    public final com.oeindevelopteam.tasknavigator.global.entity.QTimestamped _super = new com.oeindevelopteam.tasknavigator.global.entity.QTimestamped(this);

    public final com.oeindevelopteam.tasknavigator.domain.column.entity.QColumn column;

    public final NumberPath<Long> columnId = createNumber("columnId", Long.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath dueDate = createString("dueDate");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath manager = createString("manager");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final SetPath<CardTagMatches, QCardTagMatches> tagMatches = this.<CardTagMatches, QCardTagMatches>createSet("tagMatches", CardTagMatches.class, QCardTagMatches.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QCard(String variable) {
        this(Card.class, forVariable(variable), INITS);
    }

    public QCard(Path<? extends Card> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCard(PathMetadata metadata, PathInits inits) {
        this(Card.class, metadata, inits);
    }

    public QCard(Class<? extends Card> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.column = inits.isInitialized("column") ? new com.oeindevelopteam.tasknavigator.domain.column.entity.QColumn(forProperty("column"), inits.get("column")) : null;
    }

}

