package com.oeindevelopteam.tasknavigator.domain.section.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSectionStatus is a Querydsl query type for SectionStatus
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSectionStatus extends EntityPathBase<SectionStatus> {

    private static final long serialVersionUID = -1412901977L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSectionStatus sectionStatus = new QSectionStatus("sectionStatus");

    public final com.oeindevelopteam.tasknavigator.domain.board.entity.QBoard board;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath status = createString("status");

    public QSectionStatus(String variable) {
        this(SectionStatus.class, forVariable(variable), INITS);
    }

    public QSectionStatus(Path<? extends SectionStatus> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSectionStatus(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSectionStatus(PathMetadata metadata, PathInits inits) {
        this(SectionStatus.class, metadata, inits);
    }

    public QSectionStatus(Class<? extends SectionStatus> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new com.oeindevelopteam.tasknavigator.domain.board.entity.QBoard(forProperty("board")) : null;
    }

}

