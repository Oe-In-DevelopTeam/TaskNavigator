package com.oeindevelopteam.tasknavigator.domain.card.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "card_tags")
@NoArgsConstructor
public class CardTag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @OneToMany(mappedBy = "tag")
  private List<CardTagMatches> tagMatches;

  public CardTag(String name) {
    this.name = name;
  }

  public void addCardTagMatch(CardTagMatches cardTagMatches) {
    this.tagMatches.add(cardTagMatches);
  }
}
