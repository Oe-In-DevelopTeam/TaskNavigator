package com.oeindevelopteam.tasknavigator.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class UserRole {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String role;

  @OneToMany(mappedBy = "userRoleId")
  private List<UserRoleMatches> userRoleMatches = new ArrayList<>();

  public void addRole (String roleName) {
    this.role = roleName;
  }
}
