package com.oeindevelopteam.tasknavigator.domain.card.entity;

import com.oeindevelopteam.tasknavigator.domain.card.CardCardTagPK;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

@Entity
@Getter
@Table(name = "card_tag_matches")
@IdClass(CardCardTagPK.class)
@NoArgsConstructor
@EqualsAndHashCode
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
    tag.getTagMatches().add(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    CardTagMatches other = (CardTagMatches) o;
    return Objects.equals(card.getId(), other.getCard().getId()) &&
        Objects.equals(tag.getId(), other.getTag().getId());
  }

  @Override
  public int hashCode() {
    // 해시코드 -> 객체가 유일한지 알아보는 값
    return card.getId().hashCode() + tag.getId().hashCode();
  }

}
