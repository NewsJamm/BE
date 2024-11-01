package com.NewsJam.NewsJam.domain.mypage.service;

import com.NewsJam.NewsJam.domain.mypage.web.dto.ScrapNewsListResponseDto;

public interface MypageService {
    ScrapNewsListResponseDto.ScrapNewsList scrapNewsList(Long memberId);
}
