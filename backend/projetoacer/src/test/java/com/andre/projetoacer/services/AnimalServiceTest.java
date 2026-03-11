package com.andre.projetoacer.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.andre.projetoacer.domain.Animal;
import com.andre.projetoacer.repository.AnimalRepository;

@ExtendWith(MockitoExtension.class)
public class AnimalServiceTest {

	@Mock
	private AnimalRepository repository;
	
	@InjectMocks
	private AnimalService service;
	
	private Animal animal;
	
	@BeforeEach
	void setup(){
		animal = new Animal();

		animal.setId("a1b2c3d4");
		animal.setName("Thor");
		animal.setAge(3);
		animal.setWeight(18.5);
		animal.setIsAdopted(false);
		animal.setDescription("Cachorro muito dócil, gosta de brincar e se dá bem com crianças.");
	}
	
	@Test
	public void testFindAll_ShouldReturnAllAnimals() {
		//Given
		Animal animalTest = new Animal();
		animalTest.setId("a1b2c3d4");
		animalTest.setName("Loki");
		animalTest.setAge(4);
		animalTest.setWeight(20.0);
		animalTest.setIsAdopted(true);
		animalTest.setDescription("Cachorro muito estressado.");

		//When
		when(repository.findAll()).thenReturn(List.of(animal, animalTest));
		List<Animal> list = service.findAll();
		
		//Then
		assertNotNull(list);
		assertEquals(2, list.size());
	}

	@Test
	public void testSaveAnimal_ShouldReturnSavedAnimal(){
		//Given & When
		when(repository.save(animal)).thenReturn(animal);
		Animal savedAnimal = service.saveAnimal(animal);

		//Then
		assertNotNull(savedAnimal);
		assertEquals(animal.getId(), savedAnimal.getId());
	}
}
