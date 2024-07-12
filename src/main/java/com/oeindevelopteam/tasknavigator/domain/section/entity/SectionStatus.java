package com.oeindevelopteam.tasknavigator.domain.section.entity;

import com.oeindevelopteam.tasknavigator.domain.board.entity.Board;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class SectionStatus {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "sectionStatus에 상태값은 필수입니다.")
  private String status;

  @ManyToOne
  @JoinColumn(name = "board_id")
  private Board board;

  public SectionStatus(String status, Board board) {
    this.status = status;
    this.board = board;
  }
}
