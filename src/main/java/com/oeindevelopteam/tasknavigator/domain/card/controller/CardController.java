package com.oeindevelopteam.tasknavigator.domain.card.controller;

import com.oeindevelopteam.tasknavigator.domain.card.dto.CardRequestDto;
import com.oeindevelopteam.tasknavigator.domain.card.dto.CardResponseDto;
import com.oeindevelopteam.tasknavigator.domain.card.service.CardService;
import com.oeindevelopteam.tasknavigator.global.dto.CommonResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CardController {

  private final CardService cardService;

  @PostMapping("/boards/{boardId}/columns/{columnId}/cards")
  public ResponseEntity<CommonResponseDto> createCard(@PathVariable Long boardId,
      @PathVariable Long columnId, @RequestBody CardRequestDto cardRequestDto) {

    // TODO: AOP 사용해서 boardId, columnId 확인
    CardResponseDto responseDto = cardService.createdCard(cardRequestDto, columnId);

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CommonResponseDto(200, "카드 생성에 성공하였습니다.", responseDto));
  }

  // TODO: Admin인지 아닌지 확인하는 로직 필요
  @GetMapping("/admin/cards")
  public ResponseEntity<CommonResponseDto> getAllCards() {
    List<CardResponseDto> responseDtos = cardService.getAllCards();

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CommonResponseDto(200, "전체 카드 조회에 성공하였습니다.", responseDtos));
  }

  @PutMapping("/boards/{boardId}/columns/{columnId}/cards/{cardId}")
  public ResponseEntity<CommonResponseDto> editCardContent(@PathVariable Long boardId,
      @PathVariable Long columnId, @PathVariable Long cardId,
      @RequestBody CardRequestDto cardRequestDto) {

    // TODO: AOP 사용해서 boardId, columnId 확인
    CardResponseDto responseDto = cardService.editCardContent(cardRequestDto, cardId);

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CommonResponseDto(200, "카드 수정에 성공하였습니다.", responseDto));
  }

  @DeleteMapping("/boards/{boardId}/columns/{columnId}/cards/{cardId}")
  public ResponseEntity<CommonResponseDto> deleteCard(@PathVariable Long boardId,
      @PathVariable Long columnId, @PathVariable Long cardId) {

    cardService.deleteCard(cardId);

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CommonResponseDto(200, "카드 삭제에 성공하였습니다.", null));
  }

}
