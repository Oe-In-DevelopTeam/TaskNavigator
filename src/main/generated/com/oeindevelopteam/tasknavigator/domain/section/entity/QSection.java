package com.oeindevelopteam.tasknavigator.domain.section.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSection is a Querydsl query type for Section
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSection extends EntityPathBase<Section> {

    private static final long serialVersionUID = 697346901L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSection section = new QSection("section");

    public final com.oeindevelopteam.tasknavigator.domain.board.entity.QBoard board;

    public final ListPath<com.oeindevelopteam.tasknavigator.domain.card.entity.Card, com.oeindevelopteam.tasknavigator.domain.card.entity.QCard> cards = this.<com.oeindevelopteam.tasknavigator.domain.card.entity.Card, com.oeindevelopteam.tasknavigator.domain.card.entity.QCard>createList("cards", com.oeindevelopteam.tasknavigator.domain.card.entity.Card.class, com.oeindevelopteam.tasknavigator.domain.card.entity.QCard.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QSection(String variable) {
        this(Section.class, forVariable(variable), INITS);
    }

    public QSection(Path<? extends Section> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSection(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSection(PathMetadata metadata, PathInits inits) {
        this(Section.class, metadata, inits);
    }

    public QSection(Class<? extends Section> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new com.oeindevelopteam.tasknavigator.domain.board.entity.QBoard(forProperty("board")) : null;
    }

}

