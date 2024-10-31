package com.NewsJam.NewsJam.domain.news.service;

import com.NewsJam.NewsJam.domain.news.service.dto.NewsVectorRequestDTO;
import com.NewsJam.NewsJam.domain.news.service.dto.NewsVectorResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsVectorServiceImpl implements NewsVectorService {
    @Value("${ai.server.base_url}")
    private String baseUrl;
    private WebClient webClient;

    @Override
    public NewsVectorResponseDTO.VectorizeResponseDTO vectorizeNewsVector(
            NewsVectorRequestDTO.VectorizeRequestDTO request) {
        if (this.webClient == null) {
            webClient = initWebClient();
        }

        return webClient.post()
                .uri(baseUrl + "/api/news")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(NewsVectorResponseDTO.VectorizeResponseDTO.class)
                .block();
    }


    public WebClient initWebClient() {
        return WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()))
                .build();
    }

}
