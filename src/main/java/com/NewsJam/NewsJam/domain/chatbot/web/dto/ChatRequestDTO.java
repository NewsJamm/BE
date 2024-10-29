package com.NewsJam.NewsJam.domain.chatbot.web.dto;

import lombok.Data;

@Data
public class ChatRequestDTO {
    private String chat_message;
    private long news_id;
}
