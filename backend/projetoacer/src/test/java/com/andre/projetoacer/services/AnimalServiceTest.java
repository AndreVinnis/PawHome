package com.andre.projetoacer.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.andre.projetoacer.enums.*;
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

	@Test
	public void testDelete_ShouldDeleteAnimal(){
		//Given & When
		animal.setId("id");
		when(repository.findById(anyString())).thenReturn(Optional.of(animal));
		doNothing().when(repository).deleteById(animal.getId());

		service.delete(animal.getId());

		//Then
		verify(repository, times(1)).deleteById(animal.getId());
	}

	@Test
	public void testUpdate_ShouldReturnUpdatedAnimal(){
		//Given & When
		String nameTest = "Luna";
		Integer ageTest = 2;
		Double weightTest = 4.3;
		Sex sexTest = Sex.FEMALE;
		Species speciesTest = Species.CAT;
		Size sizeTest = Size.SMALL;
		Type typeTest = Type.DOMESTIC;
		Race raceTest = Race.C_BENGAL;
		String descriptionTest = "Gata muito carinhosa, tranquila e acostumada com pessoas.";

		animal.setId("id");
		when(repository.findById(anyString())).thenReturn(Optional.of(animal));
		when(repository.save(animal)).thenReturn(animal);

		Animal animalTest = service.update(nameTest, ageTest, weightTest, sexTest, speciesTest, sizeTest, typeTest, raceTest, descriptionTest, animal.getId());

		//Then
		assertNotNull(animalTest);
		assertEquals(nameTest, animalTest.getName());
		assertEquals(ageTest, animalTest.getAge());
		assertEquals(weightTest, animalTest.getWeight());
		assertEquals(sexTest, animalTest.getSex());
		assertEquals(speciesTest, animalTest.getSpecies());
		assertEquals(sizeTest, animalTest.getSize());
		assertEquals(typeTest, animalTest.getType());
		assertEquals(raceTest, animalTest.getRace());
		assertEquals(descriptionTest, animalTest.getDescription());
	}
}
