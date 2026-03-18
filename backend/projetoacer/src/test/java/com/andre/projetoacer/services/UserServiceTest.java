package com.andre.projetoacer.services;

import com.andre.projetoacer.DTO.user.UserCreationDTO;
import com.andre.projetoacer.domain.Address;
import com.andre.projetoacer.domain.User;
import com.andre.projetoacer.enums.UserRole;
import com.andre.projetoacer.repository.UserRepository;
import com.andre.projetoacer.services.exception.IncorrectInputValues;
import com.andre.projetoacer.services.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
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

    private User user;
    private UserCreationDTO userCreationDTO;

    @BeforeEach
    public void setup(){
        userCreationDTO = new UserCreationDTO(
                "André", "Silva", "12345678900", new Date(),
                "andre@email.com", "1199999999", "senha123",
                "01001000", "São Paulo", "Centro", 100, "Perto do Metrô"
        );

        Address address = new Address(userCreationDTO.cep(), userCreationDTO.city(), userCreationDTO.neighborhood(), userCreationDTO.number(), userCreationDTO.referencePoint());

        user = new User();
        user.setId("jsdias42weq5jdasjdasd");
        user.setName(userCreationDTO.name());
        user.setSecondName(userCreationDTO.secondName());
        user.setCpf(userCreationDTO.cpf());
        user.setBirthDate(userCreationDTO.birthDate());
        user.setEmail(userCreationDTO.email());
        user.setPhoneNumber(userCreationDTO.phoneNumber());
        user.setAddress(address);
    }

    @Test
    @DisplayName("Deve retornar uma lista, não vazia, com todos os usuários")
    public void testFindAll_ShouldReturnAListWithAllUsers(){
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
    public void testFindAll_WhenDontHaveUsers_ShouldReturnAEmptyListUsers(){
        List<User> expectedList = Arrays.asList();

        when(userRepository.findAll()).thenReturn(Arrays.asList());

        List<User> result = userService.findAll();

        assertNotNull(result);
        assertArrayEquals(expectedList.toArray(), result.toArray());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar um User com sucesso quando o ID existir")
    public void testFindById_WhenPassExistedId_ShouldReturnUser(){
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User result = userService.findById(user.getId());

        assertNotNull(result);
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getCpf(), result.getCpf());
        assertEquals(user.getEmail(), result.getEmail());
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    @DisplayName("Deve lançar uma exceção ObjectNotFoundException quando o ID não existir")
    public void testFindById_WhenPassNonexistedId_ShouldThrowObjectNotFoundException(){
        String expectedMessage = "User not found!";

        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        ObjectNotFoundException result = assertThrows(ObjectNotFoundException.class, () -> userService.findById(anyString()));
        assertEquals(expectedMessage, result.getMessage());
        verify(userRepository, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("Deve retornar um User com sucesso quando o email existir")
    public void testFindByEmail_WhenPassExistedEmail_ShouldReturnUser(){
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        User result = userService.findByEmail(user.getEmail());

        assertNotNull(result);
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getCpf(), result.getCpf());
        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }

    @Test
    @DisplayName("Deve lançar uma exceção ObjectNotFoundException quando o email não existir")
    public void testFindByEmail_WhenPassNonexistedEmail_ShouldThrowObjectNotFoundException(){
        String expectedMessage = "User not found!";

        when(userRepository.findByEmail(anyString())).thenReturn(null);

        ObjectNotFoundException result = assertThrows(ObjectNotFoundException.class, () -> userService.findByEmail(anyString()));
        assertEquals(expectedMessage, result.getMessage());
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    @DisplayName("Deve salvar um usuário com sucesso quando os dados forem válidos")
    public void testSaveUser_WhenPassCorrectDatas_ShouldSaveUser(){
        user.setPassword("senha_cripto");

        when(encoder.encode(anyString())).thenReturn("senha_cripto");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.saveUser(userCreationDTO, UserRole.USER);

        assertNotNull(result);
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPassword() , result.getPassword());
        assertEquals(user.getCpf(), result.getCpf());
        verify(userRepository, times(1)).save(any());
        verify(encoder, times(1)).encode(anyString());
    }

    @Test
    @DisplayName("Deve lançar uma IncorrectInputValues de usuário já cadastrado com esse email")
    public void testSaveUser_WhenPassAlreadyExistedEmail_ShouldThrowIncorrectInputValues(){
        String message = "E-mail já cadastrado no sistema!";

        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        Exception result = assertThrows(IncorrectInputValues.class, () -> userService.saveUser(userCreationDTO, UserRole.USER));
        assertNotNull(result);
        assertEquals(message, result.getMessage());
        verify(userRepository, never()).save(any());
        verify(encoder, never()).encode(anyString());
    }

    @Test
    @DisplayName("Deve fazer as alterações passadas e savar os usuários com as alterações feitas")
    public void testUpdate_WhenPassCorrectDatas_ShouldUpdateUser(){
        UserCreationDTO newUser = new UserCreationDTO(
                "Andre Vinícius",
                "Barros",
                "12345678900",
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
        Address newAddress = new Address(newUser.cep(), newUser.city(), newUser.neighborhood(), newUser.number(), newUser.referencePoint());

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(encoder.encode(anyString())).thenReturn("senha_cripto");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.update(user.getId(), newUser);

        assertEquals(newUser.name(), result.getName());
        assertEquals(newUser.email(), result.getEmail());
        assertEquals(newUser.phoneNumber(), result.getPhoneNumber());
        assertEquals("senha_cripto", result.getPassword());
        assertEquals(newAddress, result.getAddress());
        assertEquals(newUser.secondName(), result.getSecondName());
        assertEquals(newUser.cpf(), result.getCpf());
        assertEquals(newUser.birthDate(), result.getBirthDate());
    }

    @Test
    @DisplayName("Deve lançar uma exceção de ObjectNotFoundException(Usuário não encontrado) e não fazer o update")
    public void testUpdate_WhenPassNonexistedId_ShouldThrowObjectNotFoundException(){
        String message = "User not found!";

        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        Exception result = assertThrows(ObjectNotFoundException.class, () -> userService.update(user.getId(), any(UserCreationDTO.class)));
        assertNotNull(result);
        assertEquals(message, result.getMessage());
    }

    @Test
    @DisplayName("Deve salvar uma alteração na imagem do usuário com sucesso")
    public void testUploadUserImage_WhenPassCorrectDatas_ShouldUpdateUserImage(){
        MultipartFile image = new MockMultipartFile(
                "file",
                "dog-foto.jpg",
                "image/jpeg",
                "conteúdo da imagem".getBytes()
        );

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.uploadUserImage(user.getId(), image);

        assertNotNull(user.getImage());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("Deve retornar uma ObjectNotFoundException")
    public void testUploadUserImage_WhenPassNonexistedId_ShouldThrowObjectNotFoundException(){
        String message = "Usuário não encontrado";
        MultipartFile image = new MockMultipartFile(
                "file",
                "dog-foto.jpg",
                "image/jpeg",
                "conteúdo da imagem".getBytes()
        );

        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        Exception result =assertThrows(ObjectNotFoundException.class, () -> userService.uploadUserImage(user.getId(), image));
        assertNotNull(result);
        assertEquals(message, result.getMessage());
    }

    @Test
    @DisplayName("Deve apagar um usuário pelo ID com sucesso")
    public void testDeleteById_WhenPassExistedId_ShouldDeleteUser(){
        String userId = "dsadasdasdasd";
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.delete(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    @DisplayName("Deve lançar exceção e não chamar o delete quando o usuário não existir")
    void testDeleteById_WhenPassNonexistedId_ShouldThrowObjectNotFoundException() {
        String expectedMessage = "User not found!";

        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        ObjectNotFoundException result = assertThrows(ObjectNotFoundException.class, () -> {
            userService.delete(user.getId());
        });

        assertEquals(expectedMessage, result.getMessage());
        verify(userRepository, never()).deleteById(user.getId());
    }
}
