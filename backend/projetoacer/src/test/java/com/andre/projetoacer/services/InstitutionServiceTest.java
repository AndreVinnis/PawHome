package com.andre.projetoacer.services;

import com.andre.projetoacer.domain.Institution;
import com.andre.projetoacer.repository.InstitutionRepository;
import com.andre.projetoacer.services.exception.ObjectNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstitutionServiceTest {

    @Mock
    private InstitutionRepository institutionRepository;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private InstitutionService institutionService;


    @Test
    @DisplayName("Deve retornar um usuário com sucesso buscando pelo id")
    public void shouldReturnAInstitution_When_FindById(){
        Institution institution = new Institution();
        String institutionId = "dsdasdsdasdasdasd";
        String institutionName = "Vet+";
        institution.setId(institutionId);
        institution.setName(institutionName);


        when(institutionRepository.findById(institutionId)).thenReturn(Optional.of(institution));

        Institution result = institutionService.findById(institutionId);

        assertEquals(institutionName, result.getName());
        assertEquals(institutionId, result.getId());
        verify(institutionRepository, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("Deve lançar uma exceção de ObjectNotFoundException quando passado um id inexistente")
    public void shouldThrowObjectNotFoundException_With_WrongId(){
        String institutionId = "dsdasdsdasdasdasd";
        String message = "Institution not found";

        when(institutionRepository.findById(institutionId)).thenReturn(Optional.empty());

        ObjectNotFoundException result  = assertThrows(ObjectNotFoundException.class, () -> institutionService.findById(institutionId));

        assertEquals(message, result.getMessage());
        verify(institutionRepository, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("Deve retornar um usuário com sucesso buscando pelo email")
    public void shouldReturnAInstitution_When_FindByEmail(){
        Institution institution = new Institution();
        String institutionEmail = "vet+.adocao@gmail.com";
        String institutionName = "Vet+";
        institution.setEmail(institutionEmail);
        institution.setName(institutionName);


        when(institutionRepository.findByEmail(institutionEmail)).thenReturn(institution);

        Institution result = institutionService.findByEmail(institutionEmail);

        assertEquals(institutionName, result.getName());
        assertEquals(institutionEmail, result.getEmail());
        verify(institutionRepository, times(1)).findByEmail(anyString());
    }

    @Test
    @DisplayName("Deve lançar uma exceção de ObjectNotFoundException quando passado um email inexistente")
    public void shouldThrowObjectNotFoundException_With_WrongEmail(){
        String institutionEmail = "vet+.adocao@gmail.com";
        String message = "Institution not found";

        when(institutionRepository.findByEmail(institutionEmail)).thenReturn(null);

        ObjectNotFoundException result  = assertThrows(ObjectNotFoundException.class, () -> institutionService.findByEmail(institutionEmail));

        assertEquals(message, result.getMessage());
        verify(institutionRepository, times(1)).findByEmail(anyString());
    }
}