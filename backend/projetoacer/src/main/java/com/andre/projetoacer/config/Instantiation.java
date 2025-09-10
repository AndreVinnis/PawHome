package com.andre.projetoacer.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.andre.projetoacer.domain.Animal;
import com.andre.projetoacer.enums.Sex;
import com.andre.projetoacer.enums.Size;
import com.andre.projetoacer.enums.Species;
import com.andre.projetoacer.enums.Type;
import com.andre.projetoacer.repository.AnimalRepository;

@Configuration
public class Instantiation implements CommandLineRunner{
	@Autowired
	private AnimalRepository animalRepositoty;

	@Override
	public void run(String... args) throws Exception {
		animalRepositoty.deleteAll();
		Animal animal1 = new Animal("Rex", 2, 7.5, Sex.MALE, Species.DOG, Size.MEDIUM, Type.STREET, "descricao");
		animalRepositoty.saveAll(Arrays.asList(animal1));
	}

}
