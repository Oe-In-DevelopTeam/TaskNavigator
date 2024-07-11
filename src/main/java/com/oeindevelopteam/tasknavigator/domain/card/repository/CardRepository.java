package com.oeindevelopteam.tasknavigator.domain.card.repository;

import com.oeindevelopteam.tasknavigator.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

}
