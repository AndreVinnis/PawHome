package com.andre.projetoacer.services;

import com.andre.projetoacer.DTO.user.UserCreationDTO;
import com.andre.projetoacer.domain.Address;
import com.andre.projetoacer.domain.User;
import com.andre.projetoacer.enums.UserRole;
import com.andre.projetoacer.repository.UserRepository;
import com.andre.projetoacer.services.exception.ObjectNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    public void saveUserSuccessfully(){
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

    @Test
    @DisplayName("Deve retornar um User com sucesso quando o ID existir")
    public void findUserByIdSuccessfully(){
        String userId = "jsdiasjdasjdasd";
        User user = new User();
        user.setId(userId);
        user.setName("André");
        user.setEmail("andre@email.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.findById(userId);

        assertNotNull(result);
        assertEquals("André", result.getName());
        assertEquals("andre@email.com", result.getEmail());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("Deve lançar uma exceção ObjectNotFoundException quando o ID não existir")
    public void findUserByIdWithWrongId(){
        String wrongId = "fdasdasdasda";
        String expectedMessage = "User not found";

        when(userRepository.findById(wrongId)).thenReturn(Optional.empty());

        ObjectNotFoundException result = assertThrows(ObjectNotFoundException.class, () -> userService.findById(wrongId));
        assertEquals(expectedMessage, result.getMessage());
        verify(userRepository, times(1)).findById(wrongId);
    }

    @Test
    @DisplayName("Deve retornar um User com sucesso quando o email existir")
    public void findUserByEmailSuccessfully(){
        String userEmail = "andre@email.com";
        User user = new User();
        user.setName("André");
        user.setId("sdasdasdasdasd");

        when(userRepository.findByEmail(userEmail)).thenReturn(user);

        User result = userService.findByEmail(userEmail);

        assertNotNull(result);
        assertEquals("André", result.getName());
        assertEquals("sdasdasdasdasd", result.getId());
        verify(userRepository, times(1)).findByEmail(userEmail);
    }

    @Test
    @DisplayName("Deve lançar uma exceção ObjectNotFoundException quando o email não existir")
    public void findUserByEmailWithWrongEmail(){
        String wrongEmail = "andre@email.com";
        String expectedMessage = "User not found";

        when(userRepository.findByEmail(wrongEmail)).thenReturn(null);

        ObjectNotFoundException result = assertThrows(ObjectNotFoundException.class, () -> userService.findByEmail(wrongEmail));
        assertEquals(expectedMessage, result.getMessage());
        verify(userRepository, times(1)).findByEmail(wrongEmail);
    }

    @Test
    @DisplayName("Deve retornar uma lista, não vazia, com todos os usuários")
    public void findAllUserNotEmpty(){
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        User user4 = new User();
        User user5 = new User();
        user1.setName("André");
        user2.setName("Myllena");
        user3.setName("Tereza");
        user4.setName("Rafael");
        user5.setName("Luan");
        List<User> expectedList = Arrays.asList(user1, user2, user3, user4, user5);

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2, user3, user4, user5));

        List<User> result = userService.findAll();

        assertNotNull(result);
        assertArrayEquals(expectedList.toArray(), result.toArray());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia de usuários")
    public void findAllUserEmpty(){
        List<User> expectedList = Arrays.asList();

        when(userRepository.findAll()).thenReturn(Arrays.asList());

        List<User> result = userService.findAll();

        assertNotNull(result);
        assertArrayEquals(expectedList.toArray(), result.toArray());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve apagar um usuário pelo ID com sucesso")
    public void deleteUserById(){
        String userId = "dsadasdasdasd";
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.delete(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    @DisplayName("Deve lançar exceção e não chamar o delete quando o usuário não existir")
    void shouldNotDeleteWhenUserNotFound() {
        String userId = "dsadasdasdasd";
        String expectedMessage = "User not found";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ObjectNotFoundException result = assertThrows(ObjectNotFoundException.class, () -> {
            userService.delete(userId);
        });

        assertEquals(expectedMessage, result.getMessage());
        verify(userRepository, never()).deleteById(userId);
    }

    @Test
    @DisplayName("Deve fazer as alterações passadas e savar os usuários com as alterações feitas")
    public void updateUserSuccessfully(){
        User user = new User();
        UserCreationDTO newUser = new UserCreationDTO(
                "Andre Vinícius",
                "Barros",
                "123.456.789-00",
                new Date(2005, 6, 6),
                "andre@email.com",
                "88999990000",
                "senha123",
                "63000-000",
                "Juazeiro do Norte",
                "Centro",
                123,
                "Próximo à praça principal"
        );
        String userId = "123456789";
        user.setId(userId);
        user.setName("Andre");
        user.setEmail("andre@gmail.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.update(userId, newUser);

        assertEquals("Andre Vinícius", user.getName());
        assertEquals("Juazeiro do Norte", user.getAddress().getCity());
        assertEquals(new Date(2005, 6, 6), user.getBirthDate());
    }

    @Test
    @DisplayName("Deve lançar uma exceção de ObjectNotFoundException(Usuário não encontrado) e não fazer o update")
    public void shouldNotUpdateWhenUserNotFound(){
        String userId = "dsadasdasdasd";
        String expectedMessage = "User not found";
        UserCreationDTO newUser = new UserCreationDTO(
                "Andre Vinícius",
                "Barros",
                "123.456.789-00",
                new Date(2005, 6, 6),
                "andre@email.com",
                "88999990000",
                "senha123",
                "63000-000",
                "Juazeiro do Norte",
                "Centro",
                123,
                "Próximo à praça principal"
        );

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ObjectNotFoundException result = assertThrows(ObjectNotFoundException.class, () -> {
            userService.update(userId, newUser);
        });
        assertEquals(expectedMessage, result.getMessage());
    }

    @Test
    @DisplayName("Deve salvar uma alteração na imagem do usuário com sucesso")
    public void updateUserImagemSuccessfully(){
        User user = new User();
        String userId = "dsuadasdashdiausdasd";
        String userName = "André Vinícius";
        user.setId(userId);
        user.setName(userName);
        MultipartFile image = new MockMultipartFile(
                "file",
                "dog-foto.jpg",
                "image/jpeg",
                "conteúdo da imagem".getBytes()
        );

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.uploadUserImage(userId, image);

        assertNotNull(user.getImage());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("Deve retornar uma ObjectNotFoundException")
    public void shouldNotUpdateImageWhenUserNotFound(){
        User user = new User();
        String userId = "dsuadasdashdiausdasd";
        String message = "Usuário não encontrado";
        MultipartFile image = new MockMultipartFile(
                "file",
                "dog-foto.jpg",
                "image/jpeg",
                "conteúdo da imagem".getBytes()
        );

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ObjectNotFoundException result =assertThrows(ObjectNotFoundException.class, () -> userService.uploadUserImage(userId, image));

        assertEquals(message, result.getMessage());
    }
}
