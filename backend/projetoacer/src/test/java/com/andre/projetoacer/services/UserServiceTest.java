package com.andre.projetoacer.services;

import com.andre.projetoacer.DTO.user.UserCreationDTO;
import com.andre.projetoacer.domain.User;
import com.andre.projetoacer.enums.UserRole;
import com.andre.projetoacer.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Deve salvar um usuário com sucesso quando os dados forem válidos")
    public void saveUserSucessfully(){
        //Arrange(Organiza)
        UserCreationDTO userCreationDTO = new UserCreationDTO(
                "André", "Silva", "12345678900", new Date(),
                "andre@email.com", "1199999999", "senha123",
                "01001000", "São Paulo", "Centro", 100, "Perto do Metrô"
        );

        when(encoder.encode(anyString())).thenReturn("senha_cripto");
        User user = new User();
        user.setName(userCreationDTO.name());
        user.setEmail(userCreationDTO.email());
        when(userRepository.save(user)).thenReturn(user);

        //Act
        User result = userService.saveUser(userCreationDTO, UserRole.USER);

        //Assert(Verifica)
        assertNotNull(result);
        assertEquals("André", result.getName());
        assertEquals("andre@email.com", result.getEmail());

        verify(userRepository, times(1)).save(any());
        verify(encoder, times(1)).encode("senha123");
    }

    @Test
    @DisplayName("Deve retornar um erro de usuário já cadastrado com esse email")
    public void saveUserEmailAlreadyExists(){
        UserCreationDTO userCreationDTO = new UserCreationDTO(
                "André", "Silva", "12345678900", new Date(),
                "andre@email.com", "1199999999", "senha123",
                "01001000", "São Paulo", "Centro", 100, "Perto do Metrô"
        );

        when(userRepository.findByEmail("andre@email.com")).thenReturn(new User());

        assertThrows(RuntimeException.class, () -> {userService.saveUser(userCreationDTO, UserRole.USER);});

        verify(userRepository, never()).save(any());
        verify(encoder, never()).encode(anyString());
    }
}
