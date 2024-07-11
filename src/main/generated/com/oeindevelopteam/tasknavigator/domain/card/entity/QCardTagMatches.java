package com.oeindevelopteam.tasknavigator.domain.card.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCardTagMatches is a Querydsl query type for CardTagMatches
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCardTagMatches extends EntityPathBase<CardTagMatches> {

    private static final long serialVersionUID = 1999229192L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCardTagMatches cardTagMatches = new QCardTagMatches("cardTagMatches");

    public final QCard card;

    public final QCardTag tag;

    public QCardTagMatches(String variable) {
        this(CardTagMatches.class, forVariable(variable), INITS);
    }

    public QCardTagMatches(Path<? extends CardTagMatches> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCardTagMatches(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCardTagMatches(PathMetadata metadata, PathInits inits) {
        this(CardTagMatches.class, metadata, inits);
    }

    public QCardTagMatches(Class<? extends CardTagMatches> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.card = inits.isInitialized("card") ? new QCard(forProperty("card"), inits.get("card")) : null;
        this.tag = inits.isInitialized("tag") ? new QCardTag(forProperty("tag")) : null;
    }

}

