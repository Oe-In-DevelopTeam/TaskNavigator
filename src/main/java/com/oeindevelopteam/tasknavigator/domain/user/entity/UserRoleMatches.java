package com.oeindevelopteam.tasknavigator.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_role_matches")
@NoArgsConstructor
public class UserRoleMatches {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User userId;

  @Getter
  @ManyToOne
  @JoinColumn(name = "userRole_id")
  private UserRole userRoleId;

  public UserRoleMatches(User user, UserRole userRole) {
    this.userId = user;
    this.userRoleId = userRole;
  }

}
