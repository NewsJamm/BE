package com.NewsJam.NewsJam.domain.news.service;

import com.NewsJam.NewsJam.domain.news.repository.NewsRepository;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsAPIRequestDto;
import com.NewsJam.NewsJam.domain.news.web.dto.NewsAPIResponseDto;
import com.fasterxml.jackson.databind.JsonNode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;

    @Value("${naver.news.clientId}")
    private String clientId;

    @Value("${naver.news.clientSecret}")
    private String clientSecret;

    @Override
    public String getDate() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return now.format(format);
    }

    @Override
    public String getParsedString(String rawTitle) {
        String result = rawTitle.replaceAll("<b>|</b>", "");
        result = result.replaceAll("&quot;", "\"");
        log.info("::parsed String : {}::", result);
        return result;
    }

    @Override
    public List<NewsAPIResponseDto.NewsData> getNews(NewsAPIRequestDto.Keywords keywords) {
        List<NewsAPIResponseDto.NewsData> responseDto = new ArrayList<>();

        WebClient client = WebClient.builder()
                .baseUrl("https://openapi.naver.com")
                .defaultHeader("X-Naver-Client-Id", clientId)
                .defaultHeader("X-Naver-Client-Secret", clientSecret)
                .build();

        for (int i = 0; i < keywords.getKeywords().size(); i++) {
            String query = keywords.getKeywords().get(i);

            JsonNode response = client.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/search/news.json")
                            .queryParam("query", query)
                            .queryParam("display", 5)
                            .queryParam("start", 1)
                            .queryParam("sort", "date")
                            .build())
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .doOnNext(System.out::println)
                    .block();

            if (response != null && response.has("items")) {
                for (JsonNode item : response.get("items")) {
                    String rawTitle = item.get("title").asText();
                    String content = item.get("description").asText();

                    NewsAPIResponseDto.NewsData newsData = NewsAPIResponseDto.NewsData.builder()
                            .Description(getParsedString(content))
                            .originalLink(item.get("originallink").asText())
                            .pubDate(item.get("pubDate").asText())
                            .title(getParsedString(rawTitle))
                            .build();

                    log.info("::newsData::\nDiscription : {}\nTitle : {}\nOriginalLink : {}\nPubDate : {}\n",
                            newsData.getDescription(), newsData.getTitle(), newsData.getOriginalLink(),
                            newsData.getPubDate());

                    responseDto.add(newsData);
                }
            }
        }

        return responseDto;
    }


}
