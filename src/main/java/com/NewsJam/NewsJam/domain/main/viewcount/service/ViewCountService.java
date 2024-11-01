package com.NewsJam.NewsJam.domain.main.viewcount.service;

import com.NewsJam.NewsJam.domain.main.viewcount.web.dto.ViewCountIncreaseResponseDto;

public interface ViewCountService {
    ViewCountIncreaseResponseDto.response increaseViewCount(String url);
}
