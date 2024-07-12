package com.oeindevelopteam.tasknavigator.domain.section.dto;

import com.oeindevelopteam.tasknavigator.domain.section.entity.Section;
import lombok.Getter;

@Getter
public class SectionResponseDto {
  private Long id;

  public SectionResponseDto(Section section) {
    this.id = section.getId();
  }
}
