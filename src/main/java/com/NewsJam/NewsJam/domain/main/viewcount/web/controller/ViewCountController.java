package com.NewsJam.NewsJam.domain.main.viewcount.web.controller;

import com.NewsJam.NewsJam.domain.main.viewcount.service.ViewCountService;
import com.NewsJam.NewsJam.domain.main.viewcount.web.dto.ViewCountIncreaseRequestDto;
import com.NewsJam.NewsJam.domain.main.viewcount.web.dto.ViewCountIncreaseResponseDto;
import com.NewsJam.NewsJam.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "뉴스 조회수 올리는 API", description = "조회수순 정렬을 위한 뉴스 조회수 증가 API")
public class ViewCountController {
    private final ViewCountService viewCountService;

    @Operation(summary = "조회수 증가 API", description = "조회수순 정렬을 위한 뉴스 조회수 증가 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회수 증가 성공",
                    content = @Content(schema = @Schema(implementation = ViewCountIncreaseResponseDto.ViewCountIncreaseResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 오류",
                    content = @Content)
    })
    @PostMapping("/increaseViewCnt")
    public ApiResponse<ViewCountIncreaseResponseDto.ViewCountIncreaseResponse> viewCountIncrease(@RequestBody ViewCountIncreaseRequestDto.ViewCountIncreaseRequest viewCountIncreaseRequestDto){
        log.info("url = {}", viewCountIncreaseRequestDto.getUrl());

        ViewCountIncreaseResponseDto.ViewCountIncreaseResponse ViewCountIncreaseResponse = viewCountService.increaseViewCount(viewCountIncreaseRequestDto.getUrl());
        return ApiResponse.onSuccess(ViewCountIncreaseResponse);
    }

}
