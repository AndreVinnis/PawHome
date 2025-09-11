package com.andre.projetoacer.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andre.projetoacer.domain.Animal;
import com.andre.projetoacer.services.AnimalService;

@RestController
@RequestMapping(value="/animals")
public class AnimalResource {
	
	@Autowired
	private AnimalService service;
	
	@GetMapping
	public ResponseEntity<List<Animal>> findAll(){
		List<Animal> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Animal>> findById(@PathVariable String id){
		Optional<Animal> animal = service.findById(id);
		return ResponseEntity.ok().body(animal);
	}
}