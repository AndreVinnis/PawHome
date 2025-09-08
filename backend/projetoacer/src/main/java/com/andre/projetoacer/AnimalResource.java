package com.andre.projetoacer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}
