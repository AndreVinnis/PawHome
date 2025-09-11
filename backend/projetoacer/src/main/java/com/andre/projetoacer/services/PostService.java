package com.andre.projetoacer.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
