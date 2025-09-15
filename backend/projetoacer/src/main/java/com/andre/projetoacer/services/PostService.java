package com.andre.projetoacer.services;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.andre.projetoacer.DTO.AnimalDTO;
import com.andre.projetoacer.DTO.AuthorDTO;
import com.andre.projetoacer.domain.Animal;
import com.andre.projetoacer.domain.GenericUser;
import com.andre.projetoacer.domain.Post;
import com.andre.projetoacer.repository.PostRepository;
import com.andre.projetoacer.services.exception.ObjectNotFoundException;

@Service
public class PostService {
	@Autowired
	private PostRepository repository;

	public List<Post> findAll(){
		return repository.findAll();
	}
	
	public Post findById(String id) {
		Optional<Post> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
	
	public Post savePost(String title, Animal animal, GenericUser user, MultipartFile animalImage) throws IOException {
		Post post = new Post(new Date(), title, new AuthorDTO(user), new AnimalDTO(animal));
		post.setImageAnimal(animalImage.getBytes());
		//post.setImageUser(user.getImage());
		
		return repository.save(post);
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
}
