package com.oeindevelopteam.tasknavigator.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDto {
  String username;
  boolean isAdmin;
}
