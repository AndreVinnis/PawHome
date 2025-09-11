package com.andre.projetoacer.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.andre.projetoacer.domain.Animal;
import com.andre.projetoacer.repository.AnimalRepository;
import com.andre.projetoacer.services.exception.ObjectNotFoundException;

@Service
public class AnimalService {
	@Autowired
	private AnimalRepository repository;

	public List<Animal> findAll(){
		return repository.findAll();
	}
	
	public Animal findById(String id) {
		Optional<Animal> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
	
	public Animal saveAnimal(Animal animal, MultipartFile image) throws IOException {
		animal.setImage(image.getBytes());
        return repository.save(animal);
    }
}
