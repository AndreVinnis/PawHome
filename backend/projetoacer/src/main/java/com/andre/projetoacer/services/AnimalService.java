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
	
	public void delete(String id) {
		repository.deleteById(id);
	}
	
	public Animal update(Animal newObj, String id) {
		Animal inicialObj = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
		partialUpdate(inicialObj, newObj);
		return repository.save(inicialObj);   
	}
	
	private void partialUpdate(Animal inicialObj, Animal newObj) {	
		    if (newObj.getName() != null) {
				inicialObj.setName(newObj.getName());
		    }
		    if (newObj.getAge() != null) {
				inicialObj.setAge(newObj.getAge());
		    }
		    if (newObj.getWeight() != null) {
				inicialObj.setWeight(newObj.getWeight());
		    }
		    if (newObj.getSex() != null) {
				inicialObj.setSex(newObj.getSex());
		    }
		    if (newObj.getSpecies() != null) {
				inicialObj.setSpecies(newObj.getSpecies());
		    }
		    if (newObj.getSize() != null) {
				inicialObj.setSize(newObj.getSize());
		    }
		    if (newObj.getType() != null) {
				inicialObj.setType(newObj.getType());
		    }
		    if (newObj.getIsAdopted() != null) {
				inicialObj.setIsAdopted(newObj.getIsAdopted());
		    }
		    if (newObj.getDescription() != null) {
				inicialObj.setDescription(newObj.getDescription());
		    }
		    if (newObj.getRace() != null) {
				inicialObj.setRace(newObj.getRace());
		    }
		}
}
