package com.oeindevelopteam.tasknavigator.domain.card.controller;

import com.oeindevelopteam.tasknavigator.domain.card.dto.CardRequestDto;
import com.oeindevelopteam.tasknavigator.domain.card.dto.CardResponseDto;
import com.oeindevelopteam.tasknavigator.domain.card.service.CardService;
import com.oeindevelopteam.tasknavigator.global.dto.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CardController {

  private final CardService cardService;

  @PostMapping("/boards/{boardId}/columns/{columnId}/cards")
  public ResponseEntity<CommonResponseDto> createCard(@PathVariable Long boardId,
      @PathVariable Long columnId, @RequestBody CardRequestDto cardRequestDto) {
    CardResponseDto responseDto = cardService.createdCard(cardRequestDto, columnId);
    // TODO: AOP 사용해서 boardId, columnId 확인
    return ResponseEntity.status(HttpStatus.OK)
        .body(new CommonResponseDto(200, "카드 생성에 성공하였습니다.", responseDto));
  }
}
