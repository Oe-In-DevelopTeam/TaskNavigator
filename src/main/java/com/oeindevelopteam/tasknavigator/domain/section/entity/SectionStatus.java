package com.oeindevelopteam.tasknavigator.domain.section.entity;

import com.oeindevelopteam.tasknavigator.domain.board.entity.Board;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class SectionStatus {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String status;

  @ManyToOne
  @JoinColumn(name = "board_id")
  private Board baord;
}
