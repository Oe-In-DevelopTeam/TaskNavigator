package com.oeindevelopteam.tasknavigator.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserRoleMatches is a Querydsl query type for UserRoleMatches
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserRoleMatches extends EntityPathBase<UserRoleMatches> {

    private static final long serialVersionUID = -1108057544L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserRoleMatches userRoleMatches = new QUserRoleMatches("userRoleMatches");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUser userId;

    public final QUserRole userRoleId;

    public QUserRoleMatches(String variable) {
        this(UserRoleMatches.class, forVariable(variable), INITS);
    }

    public QUserRoleMatches(Path<? extends UserRoleMatches> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserRoleMatches(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserRoleMatches(PathMetadata metadata, PathInits inits) {
        this(UserRoleMatches.class, metadata, inits);
    }

    public QUserRoleMatches(Class<? extends UserRoleMatches> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userId = inits.isInitialized("userId") ? new QUser(forProperty("userId")) : null;
        this.userRoleId = inits.isInitialized("userRoleId") ? new QUserRole(forProperty("userRoleId")) : null;
    }

}

