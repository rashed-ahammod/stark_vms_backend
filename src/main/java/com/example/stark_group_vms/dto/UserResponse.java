package com.example.stark_group_vms.dto;

import com.example.stark_group_vms.entity.User;

public record UserResponse(
        Long id,
        String username,
        String fullName
) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getFullName());
    }
}
