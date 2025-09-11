package com.andre.projetoacer.resources;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andre.projetoacer.DTO.PostDTO;
import com.andre.projetoacer.domain.Post;
import com.andre.projetoacer.services.PostService;

@RestController
@RequestMapping(value="/posts")
public class PostResource {
	@Autowired
	private PostService service;
	
	@GetMapping
	public ResponseEntity<List<PostDTO>> findAll(){
		List<Post> list = service.findAll();
		List<PostDTO> listDTO = list.stream().map(x -> new PostDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Post>> findById(@PathVariable String id){
		Optional<Post> animal = service.findById(id);
		return ResponseEntity.ok().body(animal);
	}
}
