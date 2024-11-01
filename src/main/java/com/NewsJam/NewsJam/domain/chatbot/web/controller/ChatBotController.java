package com.NewsJam.NewsJam.domain.chatbot.web.controller;

import com.NewsJam.NewsJam.domain.chatbot.web.dto.ChatRequestDTO;
import com.NewsJam.NewsJam.domain.chatbot.web.dto.ChatResponseDTO;
import com.NewsJam.NewsJam.domain.chatbot.web.service.ChatBotService;
import com.NewsJam.NewsJam.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "챗봇 질문 요청 API", description = "뉴스 관련 챗봇 질문 API")
@RestController
@RequestMapping("/api/chatbot")
@RequiredArgsConstructor
public class ChatBotController {
    private final ChatBotService chatBotService;

    @Operation(summary = "뉴스 내 챗봇 질문 API", description = "뉴스와 관련된 질문 요청 챗봇 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "COMMON200",
                    description = "요청 성공",
                    content = {
                            @Content(
                                    schema = @Schema(
                                            implementation = ChatResponseDTO.class
                                    )
                            )
                    }
            )
    })
    @PostMapping("")
    public ApiResponse<ChatResponseDTO> createChat(@Valid @RequestBody ChatRequestDTO request) {
        String chat = chatBotService.createChat(request).block();

        ChatResponseDTO result = ChatResponseDTO.builder()
                .chat_answer(chat)
                .build();

        return ApiResponse.onSuccess(result);
    }

}
