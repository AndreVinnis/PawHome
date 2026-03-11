package com.andre.projetoacer.services;

import com.andre.projetoacer.domain.Institution;
import com.andre.projetoacer.domain.User;
import com.andre.projetoacer.repository.InstitutionRepository;
import com.andre.projetoacer.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    InstitutionRepository institutionRepository;

    @InjectMocks
    AuthorizationService authorizationService;

    String userEmail;
    String institutionEmail;
    String message;

    @BeforeEach
    void setup(){
        userEmail = "andre@gmail.com";
        institutionEmail = "instituicaoTest@gmail.com";
        message = "Usuário não encontrado!";
    }

    @Test
    @DisplayName("Deve retornar um usuário passando um email válido")
    public void shouldReturnUserDetails_When_PassValidUserEmail(){
        User user = new User();
        user.setEmail(userEmail);

        when(userRepository.findByEmail(userEmail)).thenReturn(user);

        UserDetails result = authorizationService.loadUserByUsername(userEmail);

        assertNotNull(result);
        verify(userRepository, times(1)).findByEmail(userEmail);
    }

    @Test
    @DisplayName("Deve retornar uma isntituição passando um email válido")
    public void shouldReturnUserDetails_When_PassValidInstitutionEmail(){
        Institution institution = new Institution();
        institution.setEmail(institutionEmail);

        when(institutionRepository.findByEmail(institutionEmail)).thenReturn(institution);

        UserDetails result = authorizationService.loadUserByUsername(institutionEmail);

        assertNotNull(result);
        verify(userRepository, times(1)).findByEmail(institutionEmail);
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando passado um email de usuário inexistente")
    public void shouldThrowUsernameNotFoundException_When_PassNonexistentUserEmail(){
        when(userRepository.findByEmail(userEmail)).thenReturn(null);

        Exception result = assertThrows(UsernameNotFoundException.class, () -> authorizationService.loadUserByUsername(userEmail));
        assertNotNull(result);
        assertInstanceOf(UsernameNotFoundException.class, result);
        assertEquals(message, result.getMessage());
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando passado um email de instituição inexistente")
    public void shouldThrowUsernameNotFoundException_When_PassNonexistentInstitutionEmail(){
        when(institutionRepository.findByEmail(institutionEmail)).thenReturn(null);

        Exception result = assertThrows(UsernameNotFoundException.class, () -> authorizationService.loadUserByUsername(institutionEmail));
        assertNotNull(result);
        assertInstanceOf(UsernameNotFoundException.class, result);
        assertEquals(message, result.getMessage());
    }
}