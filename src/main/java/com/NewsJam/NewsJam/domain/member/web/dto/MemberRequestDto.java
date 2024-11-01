package com.NewsJam.NewsJam.domain.member.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MemberRequestDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberRequest {

        @Schema(description = "사용자의 로그인 아이디", example="jinha123")
        String loginId;
        @Schema(description = "사용자의 로그인 비밀번호", example="jinhaha0312")
        String password;
        @Schema(description = "사용자의 이름", example="김진하")
        String name;
    }
}
