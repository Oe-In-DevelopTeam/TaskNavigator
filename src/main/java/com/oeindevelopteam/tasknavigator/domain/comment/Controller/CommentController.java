package com.oeindevelopteam.tasknavigator.domain.comment.Controller;

import com.oeindevelopteam.tasknavigator.domain.comment.dto.CommentRequestDto;
import com.oeindevelopteam.tasknavigator.domain.comment.dto.CommentResponseDto;
import com.oeindevelopteam.tasknavigator.domain.comment.service.CommentService;
import com.oeindevelopteam.tasknavigator.domain.user.security.UserDetailsImpl;
import com.oeindevelopteam.tasknavigator.global.dto.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @PostMapping("/boards/{boardId}/columns/{columId}/cards/{cardId}/comment")
  public ResponseEntity<CommonResponseDto> createComment(
      @PathVariable Long boardId,
      @PathVariable Long columId,
      @PathVariable Long cardId,
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody CommentRequestDto commentRequestDto) {

    CommentResponseDto commentResponseDto = commentService.createComment(cardId,
        userDetails.getUser(), commentRequestDto);

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CommonResponseDto(200, "댓글 작성에 성공하였습니다.", commentResponseDto));
  }

}
