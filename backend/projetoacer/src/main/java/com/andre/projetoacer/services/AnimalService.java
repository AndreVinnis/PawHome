package com.andre.projetoacer.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andre.projetoacer.domain.Animal;
import com.andre.projetoacer.repository.AnimalRepository;

@Service
public class AnimalService {
	@Autowired
	private AnimalRepository repository;

	public List<Animal> findAll(){
		return repository.findAll();
	}
	
	public Optional<Animal> findById(String id) {
		return repository.findById(id);
	}
}
