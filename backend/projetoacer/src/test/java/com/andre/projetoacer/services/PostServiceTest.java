package com.andre.projetoacer.services;

import com.andre.projetoacer.DTO.AnimalDTO;
import com.andre.projetoacer.DTO.post.PostCreationDTO;
import com.andre.projetoacer.DTO.user.AuthorDTO;
import com.andre.projetoacer.domain.Animal;
import com.andre.projetoacer.domain.Post;
import com.andre.projetoacer.domain.User;
import com.andre.projetoacer.enums.*;
import com.andre.projetoacer.repository.PostRepository;
import com.andre.projetoacer.services.exception.ObjectNotFoundException;
import com.andre.projetoacer.util.PostUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


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
    public void shouldReturnAListWithAllPosts(){
        Post post1 = new Post();

        when(postRepository.findAll()).thenReturn(List.of(post, post1));

        List<Post> result = postService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Deve retornar um post passando o id")
    public void shouldReturnPost_When_PassCorrectId(){
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
    @DisplayName("Deve lançar uma exceção quando passado um id inexistente")
    public void shouldThrowObjectNotFoundException_When_PassNonexistedId(){
        String message = "Objeto não encontrado!";

        when(postRepository.findById(anyString())).thenReturn(Optional.empty());

        Exception result = assertThrows(ObjectNotFoundException.class, () -> postService.findById(anyString()));
        assertNotNull(result);
        assertEquals(message, result.getMessage());
    }
}