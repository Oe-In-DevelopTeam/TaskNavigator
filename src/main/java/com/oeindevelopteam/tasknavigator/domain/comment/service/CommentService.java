package com.oeindevelopteam.tasknavigator.domain.comment.service;

import com.oeindevelopteam.tasknavigator.domain.card.entity.Card;
import com.oeindevelopteam.tasknavigator.domain.card.service.CardService;
import com.oeindevelopteam.tasknavigator.domain.comment.dto.CommentRequestDto;
import com.oeindevelopteam.tasknavigator.domain.comment.dto.CommentResponseDto;
import com.oeindevelopteam.tasknavigator.domain.comment.entity.Comment;
import com.oeindevelopteam.tasknavigator.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CardService cardService;

  @Transactional
  public CommentResponseDto createComment(Long cardId, User user,
      CommentRequestDto commentRequestDto) {
    Card card = cardService.getCardById(cardId);

    if (!cardService.findUserisAdmin(user)) {
      Long boardId = card.getBoardId();
      cardService.checkUserPermission(user, boardId);
    }

    Comment comment = new Comment(card, user.getId(), commentRequestDto.getComment());

    card.addComment(comment);

    return new CommentResponseDto(comment);
  }
}
