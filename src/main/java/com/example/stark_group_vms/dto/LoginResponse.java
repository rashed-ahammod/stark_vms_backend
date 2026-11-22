package com.example.stark_group_vms.dto;

public record LoginResponse(
        String token,
        String tokenType,
        long expiresIn,
        UserResponse user
) {
    public static LoginResponse of(String token, long expiresInSeconds, UserResponse user) {
        return new LoginResponse(token, "Bearer", expiresInSeconds, user);
    }
}
