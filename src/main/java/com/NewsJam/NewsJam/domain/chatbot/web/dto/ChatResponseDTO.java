package com.NewsJam.NewsJam.domain.chatbot.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatResponseDTO {
    @Schema(description = "챗봇 응답 메세지", example = "정말로 늦어서 죄송해요.. 바쁠텐데 언제든지 도움필요하면 도와드릴게요..")
    private String chat_answer;
}
