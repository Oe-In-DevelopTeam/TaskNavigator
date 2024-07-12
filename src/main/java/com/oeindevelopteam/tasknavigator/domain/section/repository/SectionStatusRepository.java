package com.oeindevelopteam.tasknavigator.domain.section.repository;

import com.oeindevelopteam.tasknavigator.domain.section.entity.SectionStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionStatusRepository extends JpaRepository<SectionStatus, Long> {
  Optional<SectionStatus> findByStatus(String status);
}
