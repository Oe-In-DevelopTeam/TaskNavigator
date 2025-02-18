package com.oeindevelopteam.tasknavigator.domain.section.controller;

import com.oeindevelopteam.tasknavigator.domain.section.dto.SectionRequestDto;
import com.oeindevelopteam.tasknavigator.domain.section.dto.SectionResponseDto;
import com.oeindevelopteam.tasknavigator.domain.section.service.SectionService;
import com.oeindevelopteam.tasknavigator.global.dto.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/{boardId}/columns")
public class SectionController {

  private final SectionService sectionService;
  @PostMapping()
  ResponseEntity<CommonResponseDto> createSection(@PathVariable Long boardId, @RequestBody SectionRequestDto requestDto) {

    SectionResponseDto responseDto = sectionService.createSection(boardId, requestDto);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new CommonResponseDto(HttpStatus.OK.value(), "컬럼이 생성되었습니다.", responseDto));
  }

  @DeleteMapping("/{columnId}")
  ResponseEntity<CommonResponseDto> deleteSection(@PathVariable Long boardId, @PathVariable Long columnId) {
    sectionService.deleteSection(boardId, columnId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new CommonResponseDto(HttpStatus.OK.value(), "컬럼이 삭제되었습니다.", null));
  }

//  @PutMapping("/{columnId}")
//  ResponseEntity<CommonResponseDto> updateSection(@PathVariable Long boardId, @PathVariable Long columnId, @RequestBody SectionRequestDto requestDto) {
//    sectionService.updateSection(boardId, columnId, requestDto);
//    return ResponseEntity.status(HttpStatus.OK)
//        .body(new CommonResponseDto(HttpStatus.OK.value(), "컬럼이 수정되었습니다.", null));
//  }

  @PutMapping("/{columnId}")
  ResponseEntity<CommonResponseDto> moveSection(@PathVariable Long columnId, @RequestParam int order, @RequestParam String status) {
    sectionService.moveSection(columnId, order, status);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new CommonResponseDto(HttpStatus.OK.value(), "컬럼이 이동되었습니다.", null));
  }
}
