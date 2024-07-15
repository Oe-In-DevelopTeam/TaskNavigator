package com.oeindevelopteam.tasknavigator.domain.user.entity;

import com.oeindevelopteam.tasknavigator.domain.board.entity.UserBoardMatches;
import com.oeindevelopteam.tasknavigator.domain.user.dto.UserSignupRequestDto;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String userId;

  @Column(nullable = false)
  private String password;

  @Column
  private String username;

  @Column
  private String refreshToken;

  @Column
  private String role;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserRoleMatches> userRoleMatches = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserBoardMatches> userBoardMatchesList = new ArrayList<>();

  public User(UserSignupRequestDto requestDto, UserRole userRole) {
    this.userId = requestDto.getUserId();
    this.password = requestDto.getPassword();
    this.username = requestDto.getUsername();
    this.role = userRole.getRole();

    UserRoleMatches userRoleMatch = new UserRoleMatches(this, userRole);
    this.userRoleMatches.add(userRoleMatch);
    userRole.getUserRoleMatches().add(userRoleMatch);
  }

  public void encrytionPassword(String encrytionPassword) {
    this.password = encrytionPassword;
  }

  public UserRole getUserRole() {
    if (userRoleMatches.isEmpty()) {
      return null;
    }
    return userRoleMatches.get(0).getUserRole();
  }

  public void updateRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

}
