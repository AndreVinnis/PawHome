package com.andre.projetoacer.config;

import java.util.Date;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.andre.projetoacer.DTO.AnimalDTO;
import com.andre.projetoacer.DTO.AuthorDTO;
import com.andre.projetoacer.domain.Animal;
import com.andre.projetoacer.domain.Post;
import com.andre.projetoacer.domain.User;
import com.andre.projetoacer.enums.Sex;
import com.andre.projetoacer.enums.Size;
import com.andre.projetoacer.enums.Species;
import com.andre.projetoacer.enums.Type;
import com.andre.projetoacer.repository.AnimalRepository;
import com.andre.projetoacer.repository.PostRepository;

@Configuration
public class Instantiation implements CommandLineRunner{
	@Autowired
	private AnimalRepository animalRepositoty;
	
	@Autowired
	private PostRepository postRepositoty;

	@Override
	public void run(String... args) throws Exception {
		animalRepositoty.deleteAll();
		Animal animal1 = new Animal("Rex", 2, 7.5, Sex.MALE, Species.DOG, Size.MEDIUM, Type.STREET, "descricao");
		Animal animal2 = new Animal("Lux", 2, 7.5, Sex.MALE, Species.DOG, Size.MEDIUM, Type.STREET, "descricao");
		animalRepositoty.saveAll(Arrays.asList(animal1, animal2));
		
		User user = new User("André", "andre@gmail.com" , "83 979484894", "senha", null, "Macambira", "97949494949", 20);
		postRepositoty.deleteAll();
		Post post1 = new Post(new Date(), "Estou colocando esse animal para adoção", new AuthorDTO(user), new AnimalDTO(animal1));
		postRepositoty.saveAll(Arrays.asList(post1));
	}

}
