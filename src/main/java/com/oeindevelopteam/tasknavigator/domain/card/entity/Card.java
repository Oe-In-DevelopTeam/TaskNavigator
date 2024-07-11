package com.oeindevelopteam.tasknavigator.domain.card.entity;

import com.oeindevelopteam.tasknavigator.domain.card.dto.CardRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "cards")
@NoArgsConstructor
public class Card {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false)
  private Long columnId;

  @Column(nullable = false)
  private String title;

  @Column
  private String content;

  @Column
  private String dueDate;

  @Column
  private String manager;

  public Card(CardRequestDto cardRequestDto, Long columnId, Long userId) {
    this.userId = userId;
    this.columnId = columnId;
    this.title = cardRequestDto.getTitle();
    this.content = cardRequestDto.getContent();
    this.dueDate = cardRequestDto.getDueDate();
    this.manager = cardRequestDto.getManager();
  }

}
