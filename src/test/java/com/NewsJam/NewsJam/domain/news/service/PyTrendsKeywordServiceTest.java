package com.NewsJam.NewsJam.domain.news.service;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class PyTrendsKeywordServiceTest {
    @Autowired
    private TrendKeywordService trendKeywordService;


    @Test
    @DisplayName("키워드 테스트")
    void 트렌드_키워드_호출_테스트() {
        List<String> trendKeyword = trendKeywordService.getTrendKeyword();
        for (String keyword : trendKeyword) {
            System.out.println(keyword);
        }


    }

}
