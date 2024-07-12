package com.oeindevelopteam.tasknavigator.domain.user.entity;

import com.oeindevelopteam.tasknavigator.domain.board.entity.UserBoardMatches;
import com.oeindevelopteam.tasknavigator.domain.user.dto.UserSignupRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
  private List<UserRoleMatches> userRoleMatches = new ArrayList<>();

  @OneToMany(mappedBy = "user")
  private List<UserBoardMatches> userBoardMatchesList = new ArrayList<>();

  @ElementCollection
  private List<String> passwordList = new LinkedList<>();
  private static final int PASSWORD_LENGTH = 3;

  public User(UserSignupRequestDto requestDto, UserRole userRole) {
    this.userId = requestDto.getUserId();
    this.password = requestDto.getPassword();
    this.username = requestDto.getUsername();
    this.role = userRole.getRole();
    this.userRoleMatches.add(new UserRoleMatches(this, userRole));
  }

  public void encrytionPassword(String encrytionPassword) {
    this.password = encrytionPassword;

    if (passwordList.size() >= PASSWORD_LENGTH) {
      passwordList.remove(0);
    }

    passwordList.add(encrytionPassword);
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
