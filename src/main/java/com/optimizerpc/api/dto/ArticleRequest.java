package com.optimizerpc.api.dto;

public record ArticleRequest(
        String name,
        String image,
        Double price
) {
}
