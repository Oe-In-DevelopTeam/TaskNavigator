package com.oeindevelopteam.tasknavigator.domain.card.repository;

import com.oeindevelopteam.tasknavigator.domain.card.entity.Card;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

  List<Card> findAll();

  Optional<Card> findById(Long cardId);

  List<Card> findByBoardId(Long boardId);
}
