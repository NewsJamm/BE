package com.NewsJam.NewsJam.domain.news.entity;

import com.NewsJam.NewsJam.domain.news.enums.NewsCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "news_title")
    private String newsTitle;

    @Column(name = "news_content")
    private String newsContent;

    @Enumerated(EnumType.STRING)
    @Column(name = "news_category")
    private NewsCategory newsCategory;

    @Column(name = "pubDate")
    private String pubDate;

    @Column(name = "originalLink")
    private String originalLink;

    @Column(name = "vector_idx", unique = true)
    private Long vectorIdx;

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Keyword> keywordList;

    @Column(name = "viewCnt")
    private Long viewCnt;

    // 연관관계 편의 메서드
    public void addKeyword(Keyword keyword) {
        keyword.setNews(this);
        keywordList.add(keyword);
    }

    public void increaseViewCnt() {
        System.out.println("viewCnt = "+viewCnt);
        this.viewCnt++;
    }


}
