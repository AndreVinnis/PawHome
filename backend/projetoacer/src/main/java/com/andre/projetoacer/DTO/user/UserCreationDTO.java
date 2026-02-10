package com.andre.projetoacer.DTO.user;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record UserCreationDTO(

        String name,
        String secondName,
        String cpf,
        @JsonFormat(pattern = "dd/MM/yyyy")
        Date birthDate,
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
