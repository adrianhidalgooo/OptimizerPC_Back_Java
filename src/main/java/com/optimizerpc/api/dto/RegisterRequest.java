package com.optimizerpc.api.dto;

public record RegisterRequest(
        String name,
        String email,
        String username,
        String password
) {
}
