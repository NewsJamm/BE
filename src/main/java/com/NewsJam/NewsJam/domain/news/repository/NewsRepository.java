package com.NewsJam.NewsJam.domain.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.NewsJam.NewsJam.domain.news.entity.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

}
