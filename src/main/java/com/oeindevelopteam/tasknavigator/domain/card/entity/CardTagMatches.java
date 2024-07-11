package com.oeindevelopteam.tasknavigator.domain.card.entity;

import com.oeindevelopteam.tasknavigator.domain.card.CardCardTagPK;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "card_tag_matches")
@IdClass(CardCardTagPK.class)
@NoArgsConstructor
public class CardTagMatches {

  @Id
  @ManyToOne
  @JoinColumn(name = "card_id", nullable = false)
  private Card card;

  @Id
  @ManyToOne
  @JoinColumn(name = "tag_id", nullable = false)
  private CardTag tag;

  public CardTagMatches(Card card, CardTag tag) {
    this.card = card;
    this.tag = tag;
  }

}
