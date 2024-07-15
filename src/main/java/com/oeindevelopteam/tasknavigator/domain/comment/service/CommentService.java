package com.oeindevelopteam.tasknavigator.domain.comment.service;

import com.oeindevelopteam.tasknavigator.domain.card.entity.Card;
import com.oeindevelopteam.tasknavigator.domain.card.service.CardService;
import com.oeindevelopteam.tasknavigator.domain.comment.dto.CommentRequestDto;
import com.oeindevelopteam.tasknavigator.domain.comment.dto.CommentResponseDto;
import com.oeindevelopteam.tasknavigator.domain.comment.entity.Comment;
import com.oeindevelopteam.tasknavigator.domain.comment.repository.CommentRepository;
import com.oeindevelopteam.tasknavigator.domain.user.entity.User;
import com.oeindevelopteam.tasknavigator.global.exception.CustomException;
import com.oeindevelopteam.tasknavigator.global.exception.ErrorCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CardService cardService;
  private final CommentRepository commentRepository;

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

  public List<CommentResponseDto> getComments(Long cardId, User user) {
    Card card = cardService.getCardById(cardId);

    if (!cardService.findUserisAdmin(user)) {
      Long boardId = card.getBoardId();
      cardService.checkUserPermission(user, boardId);
    }
    List<Comment> comments = commentRepository.findAllByCard(card)
        .orElseThrow(() -> new CustomException(
            ErrorCode.COMMENT_NOT_FOUND));

    return comments.stream().map(comment -> new CommentResponseDto(comment))
        .collect(Collectors.toList());
  }
}
