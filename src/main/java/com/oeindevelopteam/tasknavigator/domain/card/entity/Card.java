package com.oeindevelopteam.tasknavigator.domain.card.entity;

import com.oeindevelopteam.tasknavigator.domain.card.dto.CardRequestDto;
import com.oeindevelopteam.tasknavigator.domain.section.entity.Section;
import com.oeindevelopteam.tasknavigator.global.entity.Timestamped;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "cards")
@NoArgsConstructor
public class Card extends Timestamped {

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

  @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CardTagMatches> tagMatches;

  @ManyToOne
  @JoinColumn(name = "'column'")
  private Section section;

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

  public void setTagMatches(Set<CardTagMatches> tagMatches) {
    this.tagMatches = tagMatches;
  }

  public void editTagMatches(Set<CardTagMatches> newTagMatches) {
    tagMatches.addAll(newTagMatches);
  }

  public void addCardTag(CardTag cardTag) {
    CardTagMatches cardTagMatches = new CardTagMatches(this, cardTag);

    this.tagMatches.add(cardTagMatches);
    cardTag.addCardTagMatch(cardTagMatches);
  }

  public void removeCardTag(CardTagMatches cardTagMatches) {
    this.tagMatches.remove(cardTagMatches);
  }
}
