package com.andre.projetoacer.services;

import com.andre.projetoacer.DTO.institution.InstitutionCreationDTO;
import com.andre.projetoacer.domain.Address;
import com.andre.projetoacer.domain.Institution;
import com.andre.projetoacer.domain.User;
import com.andre.projetoacer.repository.InstitutionRepository;
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
import java.util.List;
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

    private InstitutionCreationDTO institutionCreationDTO;
    private Institution institution;

    @BeforeEach
    public void setup(){
        institutionCreationDTO = new InstitutionCreationDTO(
                "Instituto adota-patos",
                "123456780001090",
                "Instituição voltada para adoção de animais",
                "contato@institutofuturo.com",
                "88999998888",
                "SenhaSegura123",
                "63010-010",
                "Juazeiro do Norte",
                "Centro",
                120,
                "Próximo à praça principal"
        );

        Address address = new Address(institutionCreationDTO.cep(), institutionCreationDTO.city(), institutionCreationDTO.neighborhood(), institutionCreationDTO.number(), institutionCreationDTO.referencePoint());

        institution = new Institution();
        institution.setId("dasgidu3ge12e8awd");
        institution.setName(institutionCreationDTO.name());
        institution.setCnpj(institutionCreationDTO.cnpj());
        institution.setDescription(institutionCreationDTO.description());
        institution.setEmail(institutionCreationDTO.email());
        institution.setPhoneNumber(institutionCreationDTO.phoneNumber());
        institution.setAddress(address);
    }

    @Test
    @DisplayName("Deve retornar uma lista com todos os usuários")
    public void testFindAll_ShouldReturnAListWithAllInstitutions(){
        Institution institution1 = new Institution();
        Institution institution2 = new Institution();
        institution1.setName("Instituição 1");
        institution2.setName("Instituição 2");
        List<Institution> institutionsList = Arrays.asList(institution, institution1, institution2);

        when(institutionRepository.findAll()).thenReturn(institutionsList);

        List<Institution> result = institutionService.findAll();

        assertNotNull(result);
        assertArrayEquals(institutionsList.toArray(), result.toArray());
        assertEquals(3, result.size());
        verify(institutionRepository, times(1)).findAll();
    }


    @Test
    @DisplayName("Deve retornar um usuário com sucesso buscando pelo id")
    public void testFindById_WhenPassExistedId_ShouldReturnInstitution(){
        when(institutionRepository.findById(institution.getId())).thenReturn(Optional.of(institution));

        Institution result = institutionService.findById(institution.getId());

        assertEquals(institution.getName(), result.getName());
        assertEquals(institution.getId(), result.getId());
        assertEquals(institution.getCnpj(), result.getCnpj());
        verify(institutionRepository, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("Deve lançar uma exceção de ObjectNotFoundException quando passado um id inexistente")
    public void testFindById_WhenPassNonexistedId_ShouldThrowObjectNotFoundException(){
        String message = "Institution not found!";

        when(institutionRepository.findById(anyString())).thenReturn(Optional.empty());

        ObjectNotFoundException result  = assertThrows(ObjectNotFoundException.class, () -> institutionService.findById(institution.getId()));

        assertEquals(message, result.getMessage());
        verify(institutionRepository, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("Deve retornar um usuário com sucesso buscando pelo email")
    public void testFindByEmail_WhenPassExistedEmail_ShouldReturnInstitution(){
        when(institutionRepository.findByEmail(institution.getEmail())).thenReturn(institution);

        Institution result = institutionService.findByEmail(institution.getEmail());

        assertEquals(institution.getName(), result.getName());
        assertEquals(institution.getEmail(), result.getEmail());
        assertEquals(institution.getCnpj(), result.getCnpj());
        verify(institutionRepository, times(1)).findByEmail(anyString());
    }

    @Test
    @DisplayName("Deve lançar uma exceção de ObjectNotFoundException quando passado um email inexistente")
    public void testFindByEmail_WhenPassNonexistedEmail_ShouldThrowObjectNotFoundException(){
        String message = "Institution not found!";

        when(institutionRepository.findByEmail(anyString())).thenReturn(null);

        Exception result  = assertThrows(ObjectNotFoundException.class, () -> institutionService.findByEmail(anyString()));
        assertNotNull(result);
        assertEquals(message, result.getMessage());
        verify(institutionRepository, times(1)).findByEmail(anyString());
    }

    @Test
    @DisplayName("Deve retornar um usuário com sucesso buscando pelo cnpj")
    public void testFindByCnpj_WhenPassExistedCnpj_ShouldReturnInstitution(){
        when(institutionRepository.findByCnpj(institution.getCnpj())).thenReturn(Optional.of(institution));

        Institution result = institutionService.findByCnpj(institution.getCnpj());

        assertEquals(institution.getName(), result.getName());
        assertEquals(institution.getEmail(), result.getEmail());
        assertEquals(institution.getCnpj(), result.getCnpj());
        verify(institutionRepository, times(1)).findByCnpj(anyString());
    }

    @Test
    @DisplayName("Deve lançar uma exceção de ObjectNotFoundException quando passado um cnpj inexistente")
    public void testFindByCnpj_WhenPassNonexistedCnpj_ShouldThrowObjectNotFoundException(){
        String message = "Institution not found!";

        when(institutionRepository.findByCnpj(anyString())).thenReturn(Optional.empty());

        ObjectNotFoundException result  = assertThrows(ObjectNotFoundException.class, () -> institutionService.findByCnpj(anyString()));

        assertEquals(message, result.getMessage());
        verify(institutionRepository, times(1)).findByCnpj(anyString());
    }

    @Test
    @DisplayName("Deve salvar um usuário com sucesso")
    public  void testSaveInstitution_WhenPassCorrectDatas_ShouldSaveInstitution(){
        institution.setPassword("senha-crypto");

        when(encoder.encode(anyString())).thenReturn("senha-crypto");
        when(institutionRepository.save(any(Institution.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Institution result = institutionService.saveInstitution(institutionCreationDTO);

        assertNotNull(result);
        assertEquals(institutionCreationDTO.name(), result.getName());
        assertEquals(institutionCreationDTO.cnpj(), result.getCnpj());
        assertEquals(institutionCreationDTO.description(), result.getDescription());
        assertEquals(institutionCreationDTO.email(), result.getEmail());
        assertEquals("senha-crypto", result.getPassword());
        assertEquals(institution.getAddress(), result.getAddress());
        verify(encoder, times(1)).encode(anyString());
        verify(institutionRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve lançar uma RuntimeException de instituição já cadastrada com esse email")
    public void testSaveInstitution_WhenPassAlreadyExistedEmail_ShouldThrowRuntimeException(){
        String message = "E-mail já cadastrado no sistema!";

        when(institutionRepository.findByEmail(anyString())).thenReturn(institution);

        Exception result = assertThrows(RuntimeException.class, () -> institutionService.saveInstitution(institutionCreationDTO));
        assertNotNull(result);
        assertEquals(message, result.getMessage());
        verify(institutionRepository, times(1)).findByEmail(anyString());
    }

    @Test
    @DisplayName("Deve lançar uma RuntimeException de isntituição já cadastrada com esse cnpj")
    public void testSaveInstitution_WhenPassAlreadyExistedCnpj_ShouldThrowRuntimeException(){
        String message = "CNPJ já cadastrado no sistema!";

        when(institutionRepository.findByCnpj(anyString())).thenReturn(Optional.of(institution));

        Exception result = assertThrows(RuntimeException.class, () -> institutionService.saveInstitution(institutionCreationDTO));
        assertEquals(message, result.getMessage());
        verify(institutionRepository, times(1)).findByCnpj(anyString());
    }

    @Test
    @DisplayName("Deve salvar a imagem da instituição com sucesso")
    public void testUploadInstitutionImage_WhenPassCorrectDatas_ShouldUploadInstitutionImage(){
        MultipartFile image = new MockMultipartFile(
                "file",
                "dog-foto.jpg",
                "image/jpeg",
                "conteúdo da imagem".getBytes()
        );

        when(institutionRepository.findById(institution.getId())).thenReturn(Optional.of(institution));
        when(institutionRepository.save(any(Institution.class))).thenAnswer(invocation -> invocation.getArgument(0));

        institutionService.uploadInstitutionImage(institution.getId(), image);

        assertNotNull(institution.getImage());
        verify(institutionRepository, times(1)).save(institution);
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando a instituição não for encontrada")
    public void testUploadInstitutionImage_WhenPassNonexistedId_ShouldThrowObjectNotFoundException(){
        MultipartFile image = new MockMultipartFile(
                "file",
                "dog-foto.jpg",
                "image/jpeg",
                "conteúdo da imagem".getBytes()
        );

        when(institutionRepository.findById(anyString())).thenReturn(Optional.empty());

        Exception result = assertThrows(ObjectNotFoundException.class, () -> institutionService.uploadInstitutionImage(anyString(), image));
        assertNotNull(result);
        verify(institutionRepository, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("Deve atualizar o cadastro da instituição")
    public void testUpdateInstitution_WhenPassCorrectDatas_ShouldUpdateInstitution(){
        InstitutionCreationDTO institutionCreationDTO2 = new InstitutionCreationDTO(
                "Casa dos Animais",
                "98765432000155",
                "Organização dedicada ao resgate e cuidado de animais abandonados",
                "contato@casadosanimais.org",
                "88988887777",
                "OutraSenhaForte456",
                "63020-000",
                "Crato",
                "Pimenta",
                45,
                "Ao lado da escola municipal"
        );
        Address address = new Address(institutionCreationDTO2.cep(), institutionCreationDTO2.city(), institutionCreationDTO2.neighborhood(), institutionCreationDTO2.number(), institutionCreationDTO2.referencePoint());

        when(institutionRepository.findById(institution.getId())).thenReturn(Optional.of(institution));
        when(encoder.encode(anyString())).thenReturn("senha-crypto");
        when(institutionRepository.save(any(Institution.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Institution result = institutionService.update(institution.getId(), institutionCreationDTO2);

        assertNotNull(result);
        assertEquals(institutionCreationDTO2.name(), result.getName());
        assertEquals(institutionCreationDTO2.cnpj(), result.getCnpj());
        assertEquals(institutionCreationDTO2.description(), result.getDescription());
        assertEquals(institutionCreationDTO2.email(), result.getEmail());
        assertEquals(institutionCreationDTO2.phoneNumber(), result.getPhoneNumber());
        assertEquals("senha-crypto", result.getPassword());
        assertEquals(address, result.getAddress());
        verify(institutionRepository, times(1)).save(institution);
    }

    @Test
    @DisplayName("Deve lançar uma exceção ObjectNotFoundException e não fazer a update")
    public void testUpdateInstitution_WhenPassNonexistedId_ShouldThrowObjectNotFoundException(){
        String message = "Institution not found!";

        when(institutionRepository.findById(anyString())).thenReturn(Optional.empty());

        Exception result = assertThrows(ObjectNotFoundException.class, () -> institutionService.update(anyString(), institutionCreationDTO));
        assertNotNull(result);
        assertEquals(message, result.getMessage());
        verify(institutionRepository, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("Deve deletar uma instituição com sucesso")
    public void testDeleteInstitution_WhenPassExistedId_ShouldDeleteInstitution(){
        when(institutionRepository.findById(institution.getId())).thenReturn(Optional.of(institution));

        institutionService.delete(institution.getId());

        verify(institutionRepository, times(1)).deleteById(institution.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção e não chamar o delete quando a instituição não existir")
    public void testDeleteInstitution_WhenPassNonexistedId_ShouldThrowObjectNotFoundException() {
        String expectedMessage = "Institution not found!";

        when(institutionRepository.findById(anyString())).thenReturn(Optional.empty());

        Exception result = assertThrows(ObjectNotFoundException.class, () -> institutionService.delete(anyString()));
        assertNotNull(result);
        assertEquals(expectedMessage, result.getMessage());
        verify(institutionRepository, never()).deleteById(anyString());
    }
}