package com.oeindevelopteam.tasknavigator.domain.card.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCardTag is a Querydsl query type for CardTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCardTag extends EntityPathBase<CardTag> {

    private static final long serialVersionUID = -1090115157L;

    public static final QCardTag cardTag = new QCardTag("cardTag");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final ListPath<CardTagMatches, QCardTagMatches> tagMatches = this.<CardTagMatches, QCardTagMatches>createList("tagMatches", CardTagMatches.class, QCardTagMatches.class, PathInits.DIRECT2);

    public QCardTag(String variable) {
        super(CardTag.class, forVariable(variable));
    }

    public QCardTag(Path<? extends CardTag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCardTag(PathMetadata metadata) {
        super(CardTag.class, metadata);
    }

}

