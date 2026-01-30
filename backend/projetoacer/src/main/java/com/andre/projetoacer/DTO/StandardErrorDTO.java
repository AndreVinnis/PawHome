package com.andre.projetoacer.DTO;

public record StandardErrorDTO(
        Integer status,
        String message,
        Long timestamp
) {
}
