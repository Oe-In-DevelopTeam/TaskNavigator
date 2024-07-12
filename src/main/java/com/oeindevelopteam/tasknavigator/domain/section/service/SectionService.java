package com.oeindevelopteam.tasknavigator.domain.section.service;

import com.oeindevelopteam.tasknavigator.domain.board.entity.Board;
import com.oeindevelopteam.tasknavigator.domain.board.repository.BoardRepository;
import com.oeindevelopteam.tasknavigator.domain.section.dto.SectionRequestDto;
import com.oeindevelopteam.tasknavigator.domain.section.dto.SectionResponseDto;
import com.oeindevelopteam.tasknavigator.domain.section.entity.Section;
import com.oeindevelopteam.tasknavigator.domain.section.entity.SectionStatus;
import com.oeindevelopteam.tasknavigator.domain.section.repository.SectionRepository;
import com.oeindevelopteam.tasknavigator.domain.section.repository.SectionStatusRepository;
import com.oeindevelopteam.tasknavigator.global.exception.CustomException;
import com.oeindevelopteam.tasknavigator.global.exception.ErrorCode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SectionService {

  private final SectionRepository sectionRepository;
  private final SectionStatusRepository sectionStatusRepository;
  private final BoardRepository boardRepository; // TODO board Repository에 id로 객체 가져오는 코드가 생성되면 수정 예정

  @Transactional
  public SectionResponseDto createSection(Long boardId, SectionRequestDto requestDto) {

    Optional<SectionStatus> status = sectionStatusRepository.findByStatus(requestDto.getStatus());
    if (status.isPresent()) {
      throw new CustomException(ErrorCode.DUPLICATE_STATUS);
    }

    // 보드 생성과 BoardService에 getBoard가 완료되면 아래 코드로 바꿀 예정
//    Board board = boardService.getBoard(boardId);

    // 보드 생성이 되지 않아 임시로 저장하는 코드
    Board board = new Board("testBoard", "test");
    boardRepository.save(board);

    SectionStatus newStatus = new SectionStatus(requestDto.getStatus(), board);
    sectionStatusRepository.save(newStatus);

    Section newSection = new Section(board, requestDto);
    Section saveSection = sectionRepository.save(newSection);

    return new SectionResponseDto(saveSection);
  }

  @Transactional
  public void deleteSection(Long boardId, Long columnId) {
    Section section = sectionRepository.findById(columnId).orElseThrow(() ->
        new CustomException(ErrorCode.SECTION_NOT_FOUND));

    // TODO: 이 부분 역시 BoardService에 getBoard가 구현되면 바뀔 부분
    Board board = boardRepository.findById(boardId).orElseThrow(() ->
        new CustomException(ErrorCode.BOARD_NOT_FOUND));

    SectionStatus status = sectionStatusRepository.findByStatusAndBoard(section.getStatus(), board).orElseThrow(() ->
        new CustomException(ErrorCode.SECTION_STATUS_NOT_FOUND));

    sectionRepository.delete(section);
    sectionStatusRepository.delete(status);
  }

  public Section getSerction(Long sectionId) {
    return sectionRepository.findById(sectionId).orElseThrow(() ->
        new CustomException(ErrorCode.SECTION_NOT_FOUND));
  }
}
