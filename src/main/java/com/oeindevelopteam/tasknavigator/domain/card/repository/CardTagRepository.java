package com.oeindevelopteam.tasknavigator.domain.card.repository;

import com.oeindevelopteam.tasknavigator.domain.card.entity.CardTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardTagRepository extends JpaRepository<CardTag, Long> {

  CardTag findByName(String tagName);
}
