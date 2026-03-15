package com.andre.projetoacer.services;

import com.andre.projetoacer.DTO.institution.InstitutionCreationDTO;
import com.andre.projetoacer.domain.Institution;
import com.andre.projetoacer.domain.User;
import com.andre.projetoacer.repository.InstitutionRepository;
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

    @Test
    @DisplayName("Deve retornar uma lista com todos os usuários")
    public void testFindAll_ShouldReturnAListWithAllInstitutions(){
        Institution institution1 = new Institution();
        Institution institution2 = new Institution();
        Institution institution3 = new Institution();
        institution1.setName("Instituição 1");
        institution2.setName("Instituição 2");
        institution3.setName("Instituição 3");
        List<Institution> institutionsList = Arrays.asList(institution1, institution2, institution3);

        when(institutionRepository.findAll()).thenReturn(institutionsList);

        List<Institution> result = institutionService.findAll();

        assertNotNull(result);
        assertArrayEquals(institutionsList.toArray(), result.toArray());
        verify(institutionRepository, times(1)).findAll();
    }


    @Test
    @DisplayName("Deve retornar um usuário com sucesso buscando pelo id")
    public void testFindById_WhenPassExistedId_ShouldReturnInstitution(){
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
    public void testFindById_WhenPassNonexistedId_ShouldThrowObjectNotFoundException(){
        String institutionId = "dsdasdsdasdasdasd";
        String message = "Institution not found!";

        when(institutionRepository.findById(institutionId)).thenReturn(Optional.empty());

        ObjectNotFoundException result  = assertThrows(ObjectNotFoundException.class, () -> institutionService.findById(institutionId));

        assertEquals(message, result.getMessage());
        verify(institutionRepository, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("Deve retornar um usuário com sucesso buscando pelo email")
    public void testFindByEmail_WhenPassExistedEmail_ShouldReturnInstitution(){
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
    public void testFindByEmail_WhenPassNonexistedEmail_ShouldThrowObjectNotFoundException(){
        String institutionEmail = "vet+.adocao@gmail.com";
        String message = "Institution not found!";

        when(institutionRepository.findByEmail(institutionEmail)).thenReturn(null);

        ObjectNotFoundException result  = assertThrows(ObjectNotFoundException.class, () -> institutionService.findByEmail(institutionEmail));

        assertEquals(message, result.getMessage());
        verify(institutionRepository, times(1)).findByEmail(anyString());
    }

    @Test
    @DisplayName("Deve retornar um usuário com sucesso buscando pelo cnpj")
    public void testFindByCnpj_WhenPassExistedCnpj_ShouldReturnInstitution(){
        Institution institution = new Institution();
        String institutionCnpj = "14526378964526";
        String institutionName = "Vet+";
        institution.setCnpj(institutionCnpj);
        institution.setName(institutionName);


        when(institutionRepository.findByCnpj(institutionCnpj)).thenReturn(Optional.of(institution));

        Institution result = institutionService.findByCnpj(institutionCnpj);

        assertEquals(institutionName, result.getName());
        assertEquals(institutionCnpj, result.getCnpj());
        verify(institutionRepository, times(1)).findByCnpj(anyString());
    }

    @Test
    @DisplayName("Deve lançar uma exceção de ObjectNotFoundException quando passado um cnpj inexistente")
    public void testFindByCnpj_WhenPassNonexistedCnpj_ShouldThrowObjectNotFoundException(){
        String institutionCnpj = "14526378964526";
        String message = "Institution not found!";

        when(institutionRepository.findByCnpj(institutionCnpj)).thenReturn(Optional.empty());

        ObjectNotFoundException result  = assertThrows(ObjectNotFoundException.class, () -> institutionService.findByCnpj(institutionCnpj));

        assertEquals(message, result.getMessage());
        verify(institutionRepository, times(1)).findByCnpj(anyString());
    }

    @Test
    @DisplayName("Deve salvar um usuário com sucesso")
    public  void testSaveInstitution_WhenPassCorrectDatas_ShouldSaveInstitution(){
        InstitutionCreationDTO institutionCreationDTO = new InstitutionCreationDTO(
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
        Institution institution = new Institution();
        institution.setName(institutionCreationDTO.name());
        institution.setCnpj(institutionCreationDTO.cnpj());

        when(encoder.encode(anyString())).thenReturn("senha-crypto");
        when(institutionRepository.save(institution)).thenReturn(institution);

        Institution result = institutionService.saveInstitution(institutionCreationDTO);

        assertNotNull(result);
        assertEquals("Instituto adota-patos", result.getName());
        assertEquals("123456780001090", result.getCnpj());
        verify(institutionRepository, times(1)).save(any());
        verify(encoder, times(1)).encode(anyString());
    }

    @Test
    @DisplayName("Deve lançar uma RuntimeException de instituição já cadastrada com esse email")
    public void testSaveInstitution_WhenPassAlreadyExistedEmail_ShouldThrowRuntimeException(){
        InstitutionCreationDTO institutionCreationDTO = new InstitutionCreationDTO(
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
        String message = "E-mail já cadastrado no sistema!";
        Institution institution = new Institution();
        when(institutionRepository.findByEmail(institutionCreationDTO.email())).thenReturn(institution);

        Exception result = assertThrows(RuntimeException.class, () -> institutionService.saveInstitution(institutionCreationDTO));
        assertEquals(message, result.getMessage());
        verify(institutionRepository, times(1)).findByEmail(anyString());
    }

    @Test
    @DisplayName("Deve lançar uma RuntimeException de isntituição já cadastrada com esse cnpj")
    public void testSaveInstitution_WhenPassAlreadyExistedCnpj_ShouldThrowRuntimeException(){
        InstitutionCreationDTO institutionCreationDTO = new InstitutionCreationDTO(
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
        String message = "CNPJ já cadastrado no sistema!";
        Institution institution = new Institution();
        when(institutionRepository.findByCnpj(institutionCreationDTO.cnpj())).thenReturn(Optional.of(institution));

        Exception result = assertThrows(RuntimeException.class, () -> institutionService.saveInstitution(institutionCreationDTO));
        assertEquals(message, result.getMessage());
        verify(institutionRepository, times(1)).findByCnpj(anyString());
    }

    @Test
    @DisplayName("Deve salvar a imagem da instituição com sucesso")
    public void testUploadInstitutionImage_WhenPassCorrectDatas_ShouldUploadInstitutionImage(){
        Institution institution = new Institution();
        String institutionId = "bgidyuagsidgas";
        institution.setId(institutionId);
        MultipartFile image = new MockMultipartFile(
                "file",
                "dog-foto.jpg",
                "image/jpeg",
                "conteúdo da imagem".getBytes()
        );

        when(institutionRepository.findById(institutionId)).thenReturn(Optional.of(institution));

        institutionService.uploadInstitutionImage(institutionId, image);

        assertNotNull(institution.getImage());
        verify(institutionRepository, times(1)).save(institution);
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando a instituição não for encontrada")
    public void testUploadInstitutionImage_WhenPassNonexistedId_ShouldThrowObjectNotFoundException(){
        String institutionId = "bgidyuagsidgas";
        MultipartFile image = new MockMultipartFile(
                "file",
                "dog-foto.jpg",
                "image/jpeg",
                "conteúdo da imagem".getBytes()
        );

        when(institutionRepository.findById(institutionId)).thenReturn(Optional.empty());

        Exception result = assertThrows(ObjectNotFoundException.class, () -> institutionService.uploadInstitutionImage(institutionId, image));

        assertNotNull(result);
        verify(institutionRepository, times(1)).findById(institutionId);
    }

    @Test
    @DisplayName("Deve atualizar o cadastro da instituição")
    public void testUpdateInstitution_WhenPassCorrectDatas_ShouldUpdateInstitution(){
        InstitutionCreationDTO institutionCreationDTO = new InstitutionCreationDTO(
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
        Institution institution = new Institution();
        String institutionId = "sdasdasdasdadsad";
        institution.setId(institutionId);
        institution.setName("Instituto 1");

        when(institutionRepository.findById(institutionId)).thenReturn(Optional.of(institution));

        institutionService.update(institutionId, institutionCreationDTO);

        assertNotNull(institution);
        assertEquals("Instituto adota-patos", institution.getName());
        assertEquals("contato@institutofuturo.com", institution.getEmail());
        verify(institutionRepository, times(1)).save(institution);
    }

    @Test
    @DisplayName("Deve lançar uma exceção ObjectNotFoundException e não fazer a update")
    public void testUpdateInstitution_WhenPassNonexistedId_ShouldThrowObjectNotFoundException(){
        InstitutionCreationDTO institutionCreationDTO = new InstitutionCreationDTO(
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
        String institutionId = "sdasdasdasdadsad";
        String message = "Institution not found!";

        when(institutionRepository.findById(institutionId)).thenReturn(Optional.empty());

        Exception result = assertThrows(ObjectNotFoundException.class, () -> institutionService.update(institutionId, institutionCreationDTO));
        assertNotNull(result);
        assertEquals(message, result.getMessage());
    }

    @Test
    @DisplayName("Deve deletar uma instituição com sucesso")
    public void testDeleteInstitution_WhenPassExistedId_ShouldDeleteInstitution(){
        Institution institution = new Institution();
        String institutionId = "dfadasdadsasdasdasd";
        institution.setId(institutionId);

        when(institutionRepository.findById(institutionId)).thenReturn(Optional.of(institution));

        institutionService.delete(institutionId);

        verify(institutionRepository, times(1)).deleteById(institutionId);
    }

    @Test
    @DisplayName("Deve lançar exceção e não chamar o delete quando a instituição não existir")
    public void testDeleteInstitution_WhenPassNonexistedId_ShouldThrowObjectNotFoundException() {
        String userId = "dsadasdasdasd";
        String expectedMessage = "Institution not found!";

        when(institutionRepository.findById(userId)).thenReturn(Optional.empty());

        ObjectNotFoundException result = assertThrows(ObjectNotFoundException.class, () -> {
            institutionService.delete(userId);
        });

        assertEquals(expectedMessage, result.getMessage());
        verify(institutionRepository, never()).deleteById(userId);
    }
}