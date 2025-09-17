package com.andre.projetoacer.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.andre.projetoacer.domain.Animal;
import com.andre.projetoacer.enums.Race;
import com.andre.projetoacer.enums.Sex;
import com.andre.projetoacer.enums.Size;
import com.andre.projetoacer.enums.Species;
import com.andre.projetoacer.enums.Type;
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
		findById(id);
		repository.deleteById(id);
	}
	
	public Animal update(String name, Integer age, Double weight, Sex sex,
			Species species, Size size,	Type type, Race race, String description, String id) {
		Animal inicialObj = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
		partialUpdate(inicialObj, name, age, weight, sex, species, size, type, race, description);
		return repository.save(inicialObj);   
	}
	
	private void partialUpdate(Animal inicialObj, String name, Integer age, Double weight, Sex sex,
			Species species, Size size,	Type type, Race race, String description) {	
		    if (name != null) {
				inicialObj.setName(name);
		    }
		    if (age != null) {
				inicialObj.setAge(age);
		    }
		    if (weight != null) {
				inicialObj.setWeight(weight);
		    }
		    if (sex != null) {
				inicialObj.setSex(sex);
		    }
		    if (species != null) {
				inicialObj.setSpecies(species);
		    }
		    if (size != null) {
				inicialObj.setSize(size);
		    }
		    if (type != null) {
				inicialObj.setType(type);
		    }
		    if (description != null) {
				inicialObj.setDescription(description);
		    }
		    if (race != null) {
				inicialObj.setRace(race);
		    }
	}
}
