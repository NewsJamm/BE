package com.NewsJam.NewsJam.domain.scrap.web.controller;

import com.NewsJam.NewsJam.domain.member.entity.Member;
import com.NewsJam.NewsJam.domain.scrap.Service.ScrapService;
import com.NewsJam.NewsJam.domain.scrap.web.dto.ScrapRequestDto;
import com.NewsJam.NewsJam.domain.scrap.web.dto.ScrapResponseDto;
import com.NewsJam.NewsJam.domain.scrap.web.dto.ScrapUndoRequestDto;
import com.NewsJam.NewsJam.domain.scrap.web.dto.ScrapUndoResponseDto;
import com.NewsJam.NewsJam.global.annotation.LoginMember;
import com.NewsJam.NewsJam.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "뉴스 스크랩 기능 API", description = "뉴스 스크랩, 스크랩 Undo 기능 API")
public class ScrapController {
    private final ScrapService scrapService;

    @Operation(summary = "Scrap content", description = "Save scrap for a specific member.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Scrap saved successfully",
                    content = @Content(schema = @Schema(implementation = ScrapResponseDto.ScrapResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content()),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PostMapping("/scrap")
    public ApiResponse<ScrapResponseDto.ScrapResponse> scrap(
            @Valid @RequestBody
            @Parameter(description = "Details of the scrap request") ScrapRequestDto.ScrapRequest scrapRequestDto, @ApiIgnore @LoginMember Member member) {
        log.info("scrap request: {}", scrapRequestDto);
        log.info("memberId: {}", member.getId());

        ScrapResponseDto.ScrapResponse response = scrapService.scrap(scrapRequestDto, member.getId());
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "Undo scrap", description = "Undo a previously saved scrap for a specific member.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Scrap undone successfully",
                    content = @Content(schema = @Schema(implementation = ScrapUndoResponseDto.ScrapUndoResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @DeleteMapping("/scrapUndo")
    public ApiResponse<ScrapUndoResponseDto.ScrapUndoResponse> scrapUndo(
            @Valid @RequestBody
            @Parameter(description = "Details of the scrap undo request") ScrapUndoRequestDto.ScrapUndoRequest undoRequestDto, @ApiIgnore @LoginMember Member member) {
        log.info("undo request: {}", undoRequestDto);
        log.info("member: {}", member.getId());

        ScrapUndoResponseDto.ScrapUndoResponse response = scrapService.scrapUndo(undoRequestDto, member.getId());
        return ApiResponse.onSuccess(response);
    }
}
