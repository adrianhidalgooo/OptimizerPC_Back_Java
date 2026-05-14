package com.optimizerpc.api.dto;

public record LoginRequest(
        String username,
        String password
) {
}
