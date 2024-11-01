package com.NewsJam.NewsJam.domain.chatbot.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
@Schema(title = "ChatRequestDTO", description = "챗봇 요청 DTO")
public class ChatRequestDTO {
    @NotBlank(message = "질문한 내용을 입력해주세요.")
    @Schema(description = "뉴스 내용과 관련된 질문하려는 내용", example = "개발이 늦어져서 미안해요 ㅠㅠㅠ")
    private String chat_message;
    @Schema(description = "질문할 뉴스와 관련된 뉴스 고유번호 (현재 보고있는 뉴스)", example = "3")
    private long news_id;
}
