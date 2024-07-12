package com.oeindevelopteam.tasknavigator.domain.card.dto;

import java.util.Set;
import lombok.Getter;

@Getter
public class CardTagEditRequestDto {

  private Set<String> tags;
}