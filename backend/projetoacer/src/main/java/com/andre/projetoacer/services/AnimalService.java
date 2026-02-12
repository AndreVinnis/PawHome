package com.andre.projetoacer.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.andre.projetoacer.domain.Animal;
import com.andre.projetoacer.enums.PetDisease;
import com.andre.projetoacer.enums.PetMedication;
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
	
	public Animal saveAnimal(Animal animal) {
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
	
	public Animal updateMedicalRecords(List<PetDisease> diseases, List<PetMedication> medications, String id) {
		Animal obj = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
		updateParcialMedicalRecords(obj, diseases, medications);
		return repository.save(obj);
	}
	
	private void updateParcialMedicalRecords(Animal obj, List<PetDisease> diseases, List<PetMedication> medications) {
		if(!diseases.isEmpty()) {
			obj.getMedicalRecords().setDiseases(diseases);
		}
		if(!medications.isEmpty()) {
			obj.getMedicalRecords().setMedications(medications);
		}
	}
	
	public List<PetDisease> enumListPetDisease(String diseases) {
		List<PetDisease> diseasesList = Arrays.stream(diseases.split(","))
                .map(String::toUpperCase)
                .map(PetDisease::valueOf)
                .collect(Collectors.toList());
		return diseasesList;
	}

	public List<PetMedication> enumListPetMedication(String medications) {
		List<PetMedication> medicationsList = Arrays.stream(medications.split(","))
                .map(String::toUpperCase)
                .map(PetMedication::valueOf)
                .collect(Collectors.toList());
		return medicationsList;
	}
}
