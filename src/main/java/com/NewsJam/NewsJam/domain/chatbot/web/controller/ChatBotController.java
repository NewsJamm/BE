package com.NewsJam.NewsJam.domain.chatbot.web.controller;

import com.NewsJam.NewsJam.domain.chatbot.web.dto.ChatRequestDTO;
import com.NewsJam.NewsJam.domain.chatbot.web.dto.ChatResponseDTO;
import com.NewsJam.NewsJam.domain.chatbot.web.service.ChatBotService;
import com.NewsJam.NewsJam.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chatbot")
@RequiredArgsConstructor
public class ChatBotController {
    private final ChatBotService chatBotService;

    @PostMapping("")
    public ApiResponse<ChatResponseDTO> createChat(@RequestBody ChatRequestDTO request) {
        String chat = chatBotService.createChat(request).block();

        ChatResponseDTO result = ChatResponseDTO.builder()
                .chat_answer(chat)
                .build();

        return ApiResponse.onSuccess(result);
    }

}
