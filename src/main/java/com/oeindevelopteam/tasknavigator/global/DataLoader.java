package com.oeindevelopteam.tasknavigator.global;

import com.oeindevelopteam.tasknavigator.domain.card.entity.CardTag;
import com.oeindevelopteam.tasknavigator.domain.card.repository.CardTagRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

  private final CardTagRepository cardTagRepository;

  public DataLoader(CardTagRepository cardTagRepository) {
    this.cardTagRepository = cardTagRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    // 데이터베이스에 저장된 태그의 수를 확인하여 초기화할지 결정
    if (cardTagRepository.count() == 0) {
      // 초기 태그 설정
      cardTagRepository.save(new CardTag("FEATURE"));       // 새로운 기능 개발
      cardTagRepository.save(new CardTag("BUG"));           // 버그 수정
      cardTagRepository.save(new CardTag("IMPROVEMENT"));   // 기존 기능 개선
      cardTagRepository.save(new CardTag("REFACTOR"));      // 코드 리팩토링
      cardTagRepository.save(new CardTag("RESEARCH"));      // 조사 및 분석
      cardTagRepository.save(new CardTag("DOCUMENTATION")); // 문서화
      cardTagRepository.save(new CardTag("TESTING"));       // 테스트 작성 및 실행
      cardTagRepository.save(new CardTag("HIGH PRIORITY")); // 높은 우선순위
      cardTagRepository.save(new CardTag("MEDIUM PRIORITY"));// 중간 우선순위
      cardTagRepository.save(new CardTag("LOW PRIORITY"));  // 낮은 우선순위
      cardTagRepository.save(new CardTag("FRONTEND"));      // 프론트엔드 관련
      cardTagRepository.save(new CardTag("BACKEND"));       // 백엔드 관련
      cardTagRepository.save(new CardTag("DATABASE"));      // 데이터베이스 관련
      cardTagRepository.save(new CardTag("API"));           // API 관련
      cardTagRepository.save(new CardTag("UI/UX"));         // 사용자 인터페이스/경험
    }
  }
}
