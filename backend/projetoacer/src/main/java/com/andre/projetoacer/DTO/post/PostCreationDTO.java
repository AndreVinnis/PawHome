package com.andre.projetoacer.DTO.post;

import com.andre.projetoacer.enums.*;

public record PostCreationDTO(
        String title,
        Integer age,
        Double weight,
        String name,
        Sex sex,
        Species species,
        Size size,
        Type type,
        String description,
        Race race
) {
}
