package com.oeindevelopteam.tasknavigator.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1739281509L;

    public static final QUser user = new QUser("user");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath password = createString("password");

    public final ListPath<String, StringPath> passwordList = this.<String, StringPath>createList("passwordList", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath refreshToken = createString("refreshToken");

    public final StringPath role = createString("role");

    public final ListPath<com.oeindevelopteam.tasknavigator.domain.board.entity.UserBoardMatches, com.oeindevelopteam.tasknavigator.domain.board.entity.QUserBoardMatches> userBoardMatchesList = this.<com.oeindevelopteam.tasknavigator.domain.board.entity.UserBoardMatches, com.oeindevelopteam.tasknavigator.domain.board.entity.QUserBoardMatches>createList("userBoardMatchesList", com.oeindevelopteam.tasknavigator.domain.board.entity.UserBoardMatches.class, com.oeindevelopteam.tasknavigator.domain.board.entity.QUserBoardMatches.class, PathInits.DIRECT2);

    public final StringPath userId = createString("userId");

    public final StringPath username = createString("username");

    public final ListPath<UserRoleMatches, QUserRoleMatches> userRoleMatches = this.<UserRoleMatches, QUserRoleMatches>createList("userRoleMatches", UserRoleMatches.class, QUserRoleMatches.class, PathInits.DIRECT2);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

