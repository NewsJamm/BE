package com.NewsJam.NewsJam.domain.member.enums;


import java.util.Optional;

public enum AuthProvider {
    LOCAL, GOOGLE;

    public static Optional<AuthProvider> fromString(String provider) {
        for (AuthProvider authProvider : AuthProvider.values()) {
            if (authProvider.name().equalsIgnoreCase(provider)) {
                return Optional.ofNullable(authProvider);
            }
        }
        return Optional.empty();
    }

}
