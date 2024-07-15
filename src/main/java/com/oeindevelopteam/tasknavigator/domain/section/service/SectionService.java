package com.oeindevelopteam.tasknavigator.domain.section.service;

import com.oeindevelopteam.tasknavigator.domain.board.entity.Board;
import com.oeindevelopteam.tasknavigator.domain.board.repository.BoardRepository;
import com.oeindevelopteam.tasknavigator.domain.board.service.BoardService;
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
  private final BoardService boardService;

  @Transactional
  public SectionResponseDto createSection(Long boardId, SectionRequestDto requestDto) {
    if (!requestDto.getStatus().equals("New Status")) {
      Optional<SectionStatus> status = sectionStatusRepository.findByStatus(requestDto.getStatus());
      if (status.isPresent()) {
        throw new CustomException(ErrorCode.DUPLICATE_STATUS);
      }
    }

    Board board = boardService.getBoard(boardId);

    SectionStatus newStatus = new SectionStatus(requestDto.getStatus(), board);
    sectionStatusRepository.save(newStatus);

    Section newSection = new Section(board, requestDto);
    Section saveSection = sectionRepository.save(newSection);

    return new SectionResponseDto(saveSection);
  }

  @Transactional
  public void deleteSection(Long boardId, Long columnId) {
    Section section = findByIdReturnSection(columnId);

    Board board = boardService.getBoard(boardId);

    if (!section.getStatus().equals("New Status")) {
      SectionStatus status = sectionStatusRepository.findByStatusAndBoard(section.getStatus(), board).orElseThrow(() ->
          new CustomException(ErrorCode.SECTION_STATUS_NOT_FOUND));
      sectionStatusRepository.delete(status);
    }

    sectionRepository.delete(section);
  }

  public void updateSection(Long boardId, Long columnId, SectionRequestDto requestDto) {
//    if (!requestDto.getStatus().equals("New Status")) {
//      Optional<SectionStatus> status = sectionStatusRepository.findByStatus(requestDto.getStatus());
//      if (status.isPresent()) {
//        sectionStatusRepository.delete(status.get());
//      }
//    }
//
//    Section section = findByIdReturnSection(columnId);
//    section.updateSection(requestDto);
//    sectionRepository.save(section);
//
//    Section saveSection = sectionRepository.save(newSection);
//
//    return new SectionResponseDto(saveSection);
  }

  @Transactional
  public void moveSection(Long columnId, int order, String status) {
    Section section = findByIdReturnSection(columnId);

    section.updateOrder(order);
    section.updateStatus(status);

    sectionRepository.save(section);
  }

  public Section findByIdReturnSection(Long sectionId) {
    return sectionRepository.findById(sectionId).orElseThrow(() ->
        new CustomException(ErrorCode.SECTION_NOT_FOUND));
  }


  public Section getSerction(Long sectionId) {
    return sectionRepository.findById(sectionId).orElseThrow(() ->
        new CustomException(ErrorCode.SECTION_NOT_FOUND));
  }

}
