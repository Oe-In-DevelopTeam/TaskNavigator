package com.oeindevelopteam.tasknavigator.domain.card.repository;

import com.oeindevelopteam.tasknavigator.domain.card.entity.CardTag;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardTagRepository extends JpaRepository<CardTag, Long> {

  Optional<CardTag> findByName(String tagName);

  Set<CardTag> findAllByNameIn(Set<String> cardTagNames);
}
