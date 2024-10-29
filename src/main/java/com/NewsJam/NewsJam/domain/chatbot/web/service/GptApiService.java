package com.NewsJam.NewsJam.domain.chatbot.web.service;

import com.NewsJam.NewsJam.domain.chatbot.web.dto.ChatRequestDTO;
import com.NewsJam.NewsJam.domain.news.entity.News;
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

    private String generatePrompt(String chatMessage, String newsSummary) {
        return "아래의 뉴스 내용을 기반으로 질문에 대한 답변을 해줘.\n\n<뉴스 내용>\n" + newsSummary + "\n\n\n<질문 내용>\n" + chatMessage;
    }


    private Mono<String> generateResponse(String prompt) {
        String authKey = "Bearer " + GPT_API_KEY;
        log.info("authkey = {}", authKey);
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
