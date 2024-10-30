package com.NewsJam.NewsJam.domain.news.entity;

import com.NewsJam.NewsJam.domain.news.enums.NewsCategory;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    // 연관관계 편의 메서드
    public void addKeyword(Keyword keyword) {
        keyword.setNews(this);
        keywordList.add(keyword);
    }


}
