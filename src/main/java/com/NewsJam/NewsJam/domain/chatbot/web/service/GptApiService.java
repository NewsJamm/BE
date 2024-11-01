package com.NewsJam.NewsJam.domain.chatbot.web.service;

import com.NewsJam.NewsJam.domain.chatbot.web.dto.ChatRequestDTO;
import com.NewsJam.NewsJam.domain.news.entity.News;
import com.NewsJam.NewsJam.domain.news.enums.NewsCategory;
import com.NewsJam.NewsJam.domain.news.repository.NewsRepository;
import com.NewsJam.NewsJam.global.enums.statuscode.ErrorStatus;
import com.NewsJam.NewsJam.global.exception.GeneralException;
import java.util.List;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class GptApiService implements ChatBotService {
    @Value("${ai.chatbot.gpt-api}")
    private String GPT_API_KEY;
    private final WebClient webClient;
    private final NewsRepository newsRepository;

    public GptApiService(WebClient.Builder webClientBuilder, NewsRepository newsRepository) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1").build();
        this.newsRepository = newsRepository;
    }

    @Override
    public Mono<String> createChat(ChatRequestDTO request) {
        News news = newsRepository.findById(request.getNews_id())
                .orElseThrow(() -> new GeneralException(ErrorStatus._NEWS_NOT_EXIST));
        String prompt = generatePrompt(request.getChat_message(), news.getNewsContent());

        return generateResponse(prompt);
    }

    @Override
    public NewsCategory getNewsCategory(String content) {
        String categoryPrompt = generateNewsCategoryPrompt(content);
        String categoryAnswer = generateResponse(categoryPrompt).block();

        switch (categoryAnswer) {
            case "정치":
                return NewsCategory.정치;

            case "사회":
                return NewsCategory.사회;

            case "스포츠":
                return NewsCategory.스포츠;

            case "연예":
                return NewsCategory.연예;

            case "IT":
                return NewsCategory.기술;

            case "건강":
                return NewsCategory.건강;

            case "교육":
                return NewsCategory.교육;

            default:
                return NewsCategory.기타;
        }

    }

    private String generatePrompt(String chatMessage, String newsSummary) {
        return "아래의 뉴스 내용을 기반으로 질문에 대한 답변을 해줘.\n\n<뉴스 내용>\n" + newsSummary + "\n\n\n<질문 내용>\n" + chatMessage;
    }

    private String generateNewsCategoryPrompt(String content) {
        return "다음의 뉴스 내용을 기반으로 해당하는 카테고리 분류를 알려줘. 카테고리는 주어진 카테고리 목록 내에서만 골라야 해. 카테고리 분류에 해당하는 단어만 답변해줘.\n\n<뉴스 내용>\n"
                + content + "\n\n<카테고리 목록>\n정치\n사회\n경제\n스포츠\n연예\n기술\n건강\n교육\n기타";
    }


    private Mono<String> generateResponse(String prompt) {
        String authKey = "Bearer " + GPT_API_KEY;
        return webClient.post()
                .uri("/chat/completions")
                .header("Authorization", authKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new GptRequest("gpt-3.5-turbo", prompt))
                .retrieve()
                .bodyToMono(GptResponse.class)
                .map(GptResponse::getContent);
    }

    @Data
    private static class GptRequest {
        private String model;
        private List<Message> messages;

        public GptRequest(String model, String prompt) {
            this.model = model;
            this.messages = List.of(new Message("user", prompt));
        }

        @Data
        public static class Message {
            private String role;
            private String content;

            public Message(String role, String content) {
                this.role = role;
                this.content = content;
            }
        }
    }

    @Data
    private static class GptResponse {
        private List<Choice> choices;

        public String getContent() {
            return choices.get(0).message.content;
        }

        @Data
        private static class Choice {
            private Message message;
        }

        @Data
        private static class Message {
            private String role;
            private String content;
        }
    }
}
