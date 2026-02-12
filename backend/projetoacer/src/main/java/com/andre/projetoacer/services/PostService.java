package com.andre.projetoacer.services;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import com.andre.projetoacer.DTO.post.PostCreationDTO;
import com.andre.projetoacer.domain.*;
import com.andre.projetoacer.util.PostUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.andre.projetoacer.DTO.AnimalDTO;
import com.andre.projetoacer.DTO.user.AuthorDTO;
import com.andre.projetoacer.enums.Type;
import com.andre.projetoacer.repository.PostRepository;
import com.andre.projetoacer.services.exception.ObjectNotFoundException;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PostService {
	@Autowired
	private PostRepository repository;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private PostUpdater postUpdater;

	public List<Post> findAll(){
		return repository.findAll();
	}
	
	public Post findById(String id) {
		Optional<Post> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
	
	public Post savePost(PostCreationDTO post, UserDetails userDetails) {
        Animal animal = new Animal(post.name(), post.age(), post.weight(), post.sex(), post.species(), post.size(), post.type(), post.race(), post.description());
        Post newPost;
        GenericUser genericUser;

		if (userDetails instanceof Institution institution){
            genericUser = institution;
        }
        else if (userDetails instanceof User user){
            genericUser = user;
        }
        else{
            throw new ObjectNotFoundException("Usuário não encontrado");
        }

        animalService.saveAnimal(animal);
        newPost = new Post(new Date(), post.title(), new AuthorDTO(genericUser), new AnimalDTO(animal));
        postUpdater.updateListPosts(genericUser, newPost);
        return repository.save(newPost);
	}

    public void uploadAnimalImage(String id, MultipartFile file) {
        try {
            Post post = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Post não encontrado"));
            Animal animal = animalService.findById(post.getAnimalDTO().getAnimalId());
            byte[] bytes = file.getBytes();
            animal.setImage(bytes);
            animalService.saveAnimal(animal);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar a imagem");
        }
    }
	
	public void delete(String id) {
		repository.deleteById(id);
	}
	
	public Post update(Post newObj, String id) {
		Post inicialObj = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
		partialUpdate(inicialObj, newObj);
		return repository.save(inicialObj);   
	}
	
	private void partialUpdate(Post inicialObj, Post newObj) {	
		    if (newObj.getTitle() != null) {
				inicialObj.setTitle(newObj.getTitle());
		    }
		    if (newObj.getAuthor() != null) {
				inicialObj.setAuthor(newObj.getAuthor());		
			}
		    if (newObj.getAnimalDTO() != null) {
				inicialObj.setAnimalDTO(newObj.getAnimalDTO());
		    }
		    if (newObj.getImageUser() != null) {
				inicialObj.setImageUser(newObj.getImageUser());
		    }
		    if (newObj.getImageAnimal() != null) {
				inicialObj.setImageAnimal(newObj.getImageAnimal());
		    }
	}
	
	public List<Post> getStrays(){
		List<Post> animals = findAll();
		List<Post> strays = new LinkedList<>();
		
		for(Post x: animals) {
			if(x.getAnimalDTO().getType() == Type.STRAY) {
				strays.add(x);			
			}
		}	
		return strays;
	}
	
	public List<Post> getDomesticAnimals(){
		List<Post> animals = findAll();
		List<Post> pets = new LinkedList<>();
		
		for(Post x: animals) {
			if(x.getAnimalDTO().getType() == Type.DOMESTIC) {
				pets.add(x);			
			}
		}
		return pets;
	}
}
