package com.NewsJam.NewsJam.domain.scrap.web.controller;

import com.NewsJam.NewsJam.domain.member.entity.Member;
import com.NewsJam.NewsJam.domain.scrap.Service.ScrapService;
import com.NewsJam.NewsJam.domain.scrap.web.dto.ScrapRequestDto;
import com.NewsJam.NewsJam.domain.scrap.web.dto.ScrapResponseDto;
import com.NewsJam.NewsJam.domain.scrap.web.dto.ScrapUndoRequestDto;
import com.NewsJam.NewsJam.domain.scrap.web.dto.ScrapUndoResponseDto;
import com.NewsJam.NewsJam.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ScrapController {
    private final ScrapService scrapService;

    @PostMapping("/scrap")
    public ApiResponse<ScrapResponseDto.ScrapResponse> scrap(@Valid @RequestBody ScrapRequestDto.ScrapRequest scrapRequestDto) {
        log.info("scrap request: {}", scrapRequestDto);
        log.info("memberId: {}", scrapRequestDto.getMemberId());

        ScrapResponseDto.ScrapResponse response = scrapService.scrap(scrapRequestDto, scrapRequestDto.getMemberId());
        return ApiResponse.onSuccess(response);
    }

    @DeleteMapping("/scrapUndo")
    public ApiResponse<ScrapUndoResponseDto.ScrapUndoResponse> scrapUndo(@Valid @RequestBody ScrapUndoRequestDto.ScrapUndoRequest undoRequestDto) {
        log.info("undo request: {}", undoRequestDto);
        log.info("member: {}", undoRequestDto.getMemberId());

        ScrapUndoResponseDto.ScrapUndoResponse response = scrapService.scrapUndo(undoRequestDto);
        return ApiResponse.onSuccess(response);
    }
}
