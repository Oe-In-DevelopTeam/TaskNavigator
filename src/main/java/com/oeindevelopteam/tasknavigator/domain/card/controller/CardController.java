package com.oeindevelopteam.tasknavigator.domain.card.controller;

import com.oeindevelopteam.tasknavigator.domain.card.dto.CardRequestDto;
import com.oeindevelopteam.tasknavigator.domain.card.dto.CardResponseDto;
import com.oeindevelopteam.tasknavigator.domain.card.dto.CardTagEditRequestDto;
import com.oeindevelopteam.tasknavigator.domain.card.service.CardService;
import com.oeindevelopteam.tasknavigator.domain.user.security.UserDetailsImpl;
import com.oeindevelopteam.tasknavigator.global.dto.CommonResponseDto;
import com.oeindevelopteam.tasknavigator.global.exception.CustomException;
import com.oeindevelopteam.tasknavigator.global.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CardController {

  private final CardService cardService;

  @PostMapping("/boards/{boardId}/columns/{columnId}/cards")
  public ResponseEntity<CommonResponseDto> createCard(@PathVariable Long boardId,
      @PathVariable Long columnId, @RequestBody CardRequestDto cardRequestDto) {

    // TODO: 본인이 포함되어 있는 보드인지 확인 필요
    // TODO: ADMIN은 상관없이 통과

    CardResponseDto responseDto = cardService.createdCard(cardRequestDto, boardId, columnId);

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CommonResponseDto(200, "카드 생성에 성공하였습니다.", responseDto));
  }

  @PutMapping("/boards/{boardId}/columns/{columnId}/cards/{cardId}")
  public ResponseEntity<CommonResponseDto> editCardContent(@PathVariable Long boardId,
      @PathVariable Long columnId, @PathVariable Long cardId,
      @RequestBody CardRequestDto cardRequestDto) {

    // TODO: 본인이 포함되어 있는 보드인지 확인 필요
    // TODO: 본인이 작성한 카드인지 확인 필요
    // TODO: ADMIN은 상관없이 통과
    CardResponseDto responseDto = cardService.editCardContent(cardRequestDto, cardId);

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CommonResponseDto(200, "카드 수정에 성공하였습니다.", responseDto));
  }

  @DeleteMapping("/boards/{boardId}/columns/{columnId}/cards/{cardId}")
  public ResponseEntity<CommonResponseDto> deleteCard(@PathVariable Long boardId,
      @PathVariable Long columnId, @PathVariable Long cardId) {

    // TODO: 본인이 포함되어 있는 보드인지 확인 필요
    // TODO: 본인이 작성한 카드인지 확인 필요
    // TODO: ADMIN은 상관없이 통과
    cardService.deleteCard(cardId);

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CommonResponseDto(200, "카드 삭제에 성공하였습니다.", null));
  }

  @GetMapping("/boards/{boardId}/columns/{columnId}/cards/{cardId}")
  public ResponseEntity<CommonResponseDto> getCardDetail(@PathVariable Long boardId,
      @PathVariable Long columnId, @PathVariable Long cardId) {

    // TODO: 본인이 포함되어 있는 보드인지 확인 필요
    CardResponseDto responseDto = cardService.getCardDetail(cardId);

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CommonResponseDto(200, "카드 조회에 성공하였습니다.", responseDto));
  }

  @PutMapping("/boards/{boardId}/columns/{columnId}/cards/{cardId}/tags")
  public ResponseEntity<CommonResponseDto> editCardTags(@PathVariable Long boardId,
      @PathVariable Long columnId, @PathVariable Long cardId,
      @RequestBody CardTagEditRequestDto cardTagEditRequestDto) {
    // TODO: 본인이 포함되어 있는 보드인지 확인 필요
    // TODO: 본인이 작성한 카드인지 확인 필요
    // TODO: ADMIN은 상관없이 통과

    CardResponseDto responseDto = cardService.editCardTags(cardId, cardTagEditRequestDto);

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CommonResponseDto(200, "카드 태그 변경에 성공하였습니다.", responseDto));

  }

  @GetMapping("/cards")
  public ResponseEntity<CommonResponseDto> getCards(
      @RequestParam(required = false) String tag,
      @RequestParam(required = false) String manager,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    List<CardResponseDto> responseDtos;

    // 둘 다 있을 경우 오류 응답 반환
    if (tag != null && manager != null) {
      throw new CustomException(ErrorCode.MULTIPLE_PARAMETERS_NOT_ALLOWED);
    }

    if (tag != null) {
      responseDtos = cardService.getCardsByTag(tag, userDetails.getUser());
    } else if (manager != null) {
      responseDtos = cardService.getCardsByManager(manager, userDetails.getUser());
    } else {
      responseDtos = cardService.getAllCardsByInvited(userDetails.getUser());
    }

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CommonResponseDto(200, "카드 조회에 성공하였습니다.", responseDtos));
  }


}
