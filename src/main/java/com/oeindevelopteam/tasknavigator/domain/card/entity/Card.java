package com.oeindevelopteam.tasknavigator.domain.card.entity;

import com.oeindevelopteam.tasknavigator.domain.board.entity.Board;
import com.oeindevelopteam.tasknavigator.domain.card.dto.CardRequestDto;
import jakarta.persistence.*;
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

  @ManyToOne
  @JoinColumn(name = "'column'")
  private com.oeindevelopteam.tasknavigator.domain.column.entity.Column column;

  public Card(CardRequestDto cardRequestDto, Long columnId, Long userId) {
    this.userId = userId;
    this.columnId = columnId;
    this.title = cardRequestDto.getTitle();
    this.content = cardRequestDto.getContent();
    this.dueDate = cardRequestDto.getDueDate();
    this.manager = cardRequestDto.getManager();
  }

  public void editCard(CardRequestDto cardRequestDto) {
    if (cardRequestDto.getTitle() != null) {
      this.title = cardRequestDto.getTitle();
    }
    if (cardRequestDto.getContent() != null) {
      this.content = cardRequestDto.getContent();
    }
    if (cardRequestDto.getDueDate() != null) {
      this.dueDate = cardRequestDto.getDueDate();
    }
    if (cardRequestDto.getManager() != null) {
      this.manager = cardRequestDto.getManager();
    }
  }
}
