package com.NewsJam.NewsJam.domain.news.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "News")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "newsId")
    private Long newsId;

    @Column(name = "description")
    private String description;

    @Column(name = "title")
    private String title;

    @Column(name = "pubDate")
    private String pubDate;

    @Column(name = "originalLink")
    private String originalLink;
}
