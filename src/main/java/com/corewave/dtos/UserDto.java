package com.corewave.dtos;

public record UserDto(
        Integer id,
        String name,
        boolean deleted,
        String email
) {
}
