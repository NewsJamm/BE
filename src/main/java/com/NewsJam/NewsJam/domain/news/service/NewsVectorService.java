package com.NewsJam.NewsJam.domain.news.service;

import com.NewsJam.NewsJam.domain.news.entity.News;
import com.NewsJam.NewsJam.domain.news.enums.NewsCategory;


public interface NewsVectorService {
	News saveNewsWithVector(String newsTitle, String newsContent, NewsCategory newsCategory);
}
