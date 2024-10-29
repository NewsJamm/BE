package com.NewsJam.NewsJam.domain.chatbot.web.service;


import com.NewsJam.NewsJam.domain.chatbot.web.dto.ChatRequestDTO;
import reactor.core.publisher.Mono;

public interface ChatBotService {
    Mono<String> createChat(ChatRequestDTO request);
}
