package com.optimizerpc.api.dto;

import java.util.UUID;

public record LoginResponse(
        String token,
        String username,
        UUID id
) {
}
