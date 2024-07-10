package com.oeindevelopteam.tasknavigator.domain.card.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "cards")
public class Card {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private Long userId;

  @Column(nullable = false, unique = true)
  private Long columnId;

  @Column(nullable = false, unique = true)
  private String title;

  @Column
  private String content;

  @Column
  private String dueDate;

  @Column
  private String manager;
}
