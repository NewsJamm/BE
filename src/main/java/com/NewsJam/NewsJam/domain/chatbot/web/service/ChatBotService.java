package com.NewsJam.NewsJam.domain.chatbot.web.service;


import com.NewsJam.NewsJam.domain.chatbot.web.dto.ChatRequestDTO;
import com.NewsJam.NewsJam.domain.news.enums.NewsCategory;
import reactor.core.publisher.Mono;

public interface ChatBotService {
    Mono<String> createChat(ChatRequestDTO request);

    NewsCategory getNewsCategory(String content);
}
