package com.andre.projetoacer.DTO.institution;

public record InstitutionCreationDTO(
        String name,
        String cnpj,
        String description,
        String email,
        String phoneNumber,
        String password,
        String cep,
        String city,
        String neighborhood,
        Integer number,
        String referencePoint
) {
}
