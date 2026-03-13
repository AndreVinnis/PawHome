package com.andre.projetoacer.services;

import com.andre.projetoacer.DTO.AnimalDTO;
import com.andre.projetoacer.DTO.post.PostCreationDTO;
import com.andre.projetoacer.DTO.user.AuthorDTO;
import com.andre.projetoacer.domain.*;
import com.andre.projetoacer.enums.*;
import com.andre.projetoacer.repository.PostRepository;
import com.andre.projetoacer.services.exception.ObjectNotFoundException;
import com.andre.projetoacer.util.PostUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostUpdater postUpdater;

    @Mock
    private AnimalService animalService;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    private PostCreationDTO postCreationDTO;
    private Post post;

    @BeforeEach
    public void setup(){
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setUserId("dadasd243sdad232");
        authorDTO.setName("André Vinícius");

        postCreationDTO = new PostCreationDTO(
                "Cachorro para adoção",
                3,
                12.5,
                "Thor",
                Sex.MALE,
                Species.DOG,
                Size.MEDIUM,
                Type.DOMESTIC,
                "Cachorro muito dócil e brincalhão, ideal para famílias.",
                Race.C_CORNIH_REX
        );

        AnimalDTO animalDTO = new AnimalDTO();
        animalDTO.setAnimalId("dfadsadasda34234eq");
        animalDTO.setName(postCreationDTO.name());
        animalDTO.setSex(postCreationDTO.sex());
        animalDTO.setType(postCreationDTO.type());
        animalDTO.setAdopted(false);
        animalDTO.setAge(postCreationDTO.age());

        post = new Post(
                new Date(),
                postCreationDTO.title(),
                authorDTO,
                animalDTO
        );

    }

    @Test
    @DisplayName("Deve retornar uma lista com todos os posts")
    public void testFindAll_ShouldReturnAllPosts(){
        Post post1 = new Post();

        when(postRepository.findAll()).thenReturn(List.of(post, post1));

        List<Post> result = postService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Deve retornar um post passando o id")
    public void testFindById_WhenPassCorrectId_ShouldReturnPost(){
        String postId = "fadasd3e4reqda";
        post.setId(postId);

        when(postRepository.findById(postId)).thenReturn(Optional.ofNullable(post));

        Post result = postService.findById(postId);

        assertNotNull(result);
        assertEquals("dadasd243sdad232", result.getAuthor().getUserId());
        assertEquals("Thor", result.getAnimalDTO().getName());
        assertEquals("Cachorro para adoção", result.getTitle());
    }

    @Test
    @DisplayName("Deve lançar uma exceção ObjectNotFoundException quando passado um id inexistente buscando um post por id")
    public void testFindById_WhenPassNonexistedId_ShouldThrowObjectNotFoundException(){
        String message = "Objeto não encontrado!";

        when(postRepository.findById(anyString())).thenReturn(Optional.empty());

        Exception result = assertThrows(ObjectNotFoundException.class, () -> postService.findById(anyString()));
        assertNotNull(result);
        assertEquals(message, result.getMessage());
    }

    @Test
    @DisplayName("Deve salvar um post de usuário no banco de dados")
    public void testSavePost_WhenPassCorrectUserDatas_ShouldSavePost(){
        User user = new User();
        String userId = "rwqaewda3434q";
        user.setId(userId);
        String userName = "André Vinícius";
        user.setName(userName);

        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));
        doCallRealMethod().when(postUpdater).updateListPosts(eq(user), any(Post.class));

        Post result = postService.savePost(postCreationDTO, user);

        assertNotNull(result);
        assertEquals(user.getId(), result.getAuthor().getUserId());
        assertEquals(user.getName(), result.getAuthor().getName());
        assertEquals(postCreationDTO.title(), result.getTitle());
        assertEquals(postCreationDTO.name(), result.getAnimalDTO().getName());
        assertEquals(postCreationDTO.age(), result.getAnimalDTO().getAge());
        assertEquals(postCreationDTO.sex(), result.getAnimalDTO().getSex());
        assertEquals(postCreationDTO.type(), result.getAnimalDTO().getType());
        assertEquals(false, result.getAnimalDTO().getAdopted());
        assertEquals(1, user.getPosts().size());
        verify(postUpdater, times(1)).updateListPosts(user, result);
    }

    @Test
    @DisplayName("Deve salvar um post de instituição no banco de dados")
    public void testSavePost_WhenPassCorrectInstitutionDatas_ShouldSavePost(){
        Institution institution = new Institution();
        String institutionId = "rwqaewda3434q";
        institution.setId(institutionId);
        String institutionName = "André Vinícius";
        institution.setName(institutionName);

        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));
        doCallRealMethod().when(postUpdater).updateListPosts(eq(institution), any(Post.class));

        Post result = postService.savePost(postCreationDTO, institution);

        assertNotNull(result);
        assertEquals(institution.getId(), result.getAuthor().getUserId());
        assertEquals(institution.getName(), result.getAuthor().getName());
        assertEquals(postCreationDTO.title(), result.getTitle());
        assertEquals(postCreationDTO.name(), result.getAnimalDTO().getName());
        assertEquals(postCreationDTO.age(), result.getAnimalDTO().getAge());
        assertEquals(postCreationDTO.sex(), result.getAnimalDTO().getSex());
        assertEquals(postCreationDTO.type(), result.getAnimalDTO().getType());
        assertEquals(false, result.getAnimalDTO().getAdopted());
        assertEquals(1, institution.getPosts().size());
        verify(postUpdater, times(1)).updateListPosts(institution, result);
    }

    @Test
    @DisplayName("Deve fazer o upload da imagem do animal no post")
    public void testUploadAnimalImage_WhenPassCorrectAnimalImages_ShouldUpdatePost(){
        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        Animal animal = new Animal();
        MultipartFile image = new MockMultipartFile(
                "file",
                "dog-foto.jpg",
                "image/jpeg",
                "conteúdo da imagem".getBytes()
        );

        when(postRepository.findById(anyString())).thenReturn(Optional.of(post));
        when(animalService.findById(anyString())).thenReturn(animal);
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));

        postService.uploadAnimalImage(anyString(), image);

        verify(animalService, times(1)).findById(anyString());
        verify(postRepository, times(1)).findById(anyString());
        verify(postRepository, times(1)).save(postCaptor.capture());
        Post result = postCaptor.getValue();
        assertNotNull(animal.getImage());
        assertNotNull(result.getImageAnimal());
    }

    @Test
    @DisplayName("Deve lançar uma exceção ObjectNotFoundException quando passado um id inexistente fazendo upload da imagem do animal")
    public void testUploadAnimalImage_WhenPassNonExistentId_ShouldThrowObjectNotFoundException(){
        String message = "Post não encontrado!";
        MultipartFile image = new MockMultipartFile(
                "file",
                "dog-foto.jpg",
                "image/jpeg",
                "conteúdo da imagem".getBytes()
        );

        when(postRepository.findById(anyString())).thenReturn(Optional.empty());

        Exception result = assertThrows(ObjectNotFoundException.class, () -> postService.uploadAnimalImage(anyString(), image));

        verify(postRepository, times(1)).findById(anyString());
        assertNotNull(result);
        assertEquals(message, result.getMessage());
    }

    @Test
    @DisplayName("Deve deletar um post quando passado um id existente")
    public void testDeletePost_WhenPassExistentId_ShouldDeletePost(){
        post.setId("rwqaewda3434q");

        when(postRepository.findById(anyString())).thenReturn(Optional.of(post));

        postService.delete(post.getId());

        verify(postRepository, times(1)).findById(anyString());
        verify(postRepository, times(1)).deleteById(anyString());
    }

    @Test
    @DisplayName("Deve lançar uma exceção ObjectNotFoundException quando passado um id inexistente deletando um post")
    public void testDeletePost_WhenPassNonExistentId_ShouldThrowObjectNotFoundException(){
        String message = "Post não encontrado!";

        when(postRepository.findById(anyString())).thenReturn(Optional.empty());

        Exception result = assertThrows(ObjectNotFoundException.class, () -> postService.delete(anyString()));
        assertNotNull(result);
        assertEquals(message, result.getMessage());
    }

    @Test
    @DisplayName("Deve fazer a atualização do title do post")
    public void testUpdatePost_WhenPassCorrectTitle_ShouldUpdatePost(){
        String newTitle = "newTitle";

        when(postRepository.findById(anyString())).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Post result = postService.update(newTitle, anyString());

        assertNotNull(result);
        assertEquals(newTitle, result.getTitle());
        verify(postRepository, times(1)).findById(anyString());
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    @DisplayName("Deve lançar uma exceção ObjectNotFoundException quando passado um id inexistente atualizando o título do post")
    public void testUpdatePost_WhenPassNonExistentId_ShouldThrowObjectNotFoundException(){
        String message = "Post não encontrado!";

        when(postRepository.findById(anyString())).thenReturn(Optional.empty());

        Exception result = assertThrows(ObjectNotFoundException.class, () -> postService.update("Qualquer string", anyString()));
        assertNotNull(result);
        assertEquals(message, result.getMessage());
    }
}