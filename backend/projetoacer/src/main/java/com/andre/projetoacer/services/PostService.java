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

@Service
public class PostService {
	@Autowired
	private PostRepository repository;

	public List<Post> findAll(){
		return repository.findAll();
	}
	
	public Optional<Post> findById(String id) {
		return repository.findById(id);
	}
	
	public Post savePost(String title, Animal animal, GenericUser user, MultipartFile animalImage) throws IOException {
		Post post = new Post(new Date(), title, new AuthorDTO(user), new AnimalDTO(animal));
		post.setImageAnimal(animalImage.getBytes());
		//post.setImageUser(userImage.getBytes());
		
		return repository.save(post);
	}
}
