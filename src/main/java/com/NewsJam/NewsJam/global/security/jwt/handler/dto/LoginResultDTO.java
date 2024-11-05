package com.NewsJam.NewsJam.global.security.jwt.handler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResultDTO {
    private String accessToken;
}
