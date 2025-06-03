package com.corewave.dtos;

public record ExceptionDto(
        int statusCode,
        String text,
        String errorType
) {
}
