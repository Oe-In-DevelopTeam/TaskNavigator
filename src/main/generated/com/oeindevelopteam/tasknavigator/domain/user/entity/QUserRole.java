package com.oeindevelopteam.tasknavigator.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserRole is a Querydsl query type for UserRole
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserRole extends EntityPathBase<UserRole> {

    private static final long serialVersionUID = 1066897019L;

    public static final QUserRole userRole = new QUserRole("userRole");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath role = createString("role");

    public final ListPath<UserRoleMatches, QUserRoleMatches> userRoleMatches = this.<UserRoleMatches, QUserRoleMatches>createList("userRoleMatches", UserRoleMatches.class, QUserRoleMatches.class, PathInits.DIRECT2);

    public QUserRole(String variable) {
        super(UserRole.class, forVariable(variable));
    }

    public QUserRole(Path<? extends UserRole> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserRole(PathMetadata metadata) {
        super(UserRole.class, metadata);
    }

}

