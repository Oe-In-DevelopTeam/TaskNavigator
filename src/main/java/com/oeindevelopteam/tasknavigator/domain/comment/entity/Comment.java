package com.oeindevelopteam.tasknavigator.domain.comment.entity;

import com.oeindevelopteam.tasknavigator.domain.card.entity.Card;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "comments")
@NoArgsConstructor
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long userId;

  @ManyToOne
  @JoinColumn(name = "card_id", nullable = false)
  private Card card;

  @Column(nullable = false)
  private String comment;

  public Comment(Card card, Long userId, String comment) {
    this.card = card;
    this.userId = userId;
    this.comment = comment;
  }
}
