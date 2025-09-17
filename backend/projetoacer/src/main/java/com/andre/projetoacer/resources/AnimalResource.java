package com.andre.projetoacer.resources;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.andre.projetoacer.domain.Animal;
import com.andre.projetoacer.enums.PetDisease;
import com.andre.projetoacer.enums.PetMedication;
import com.andre.projetoacer.enums.Race;
import com.andre.projetoacer.enums.Sex;
import com.andre.projetoacer.enums.Size;
import com.andre.projetoacer.enums.Species;
import com.andre.projetoacer.enums.Type;
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
	
	@GetMapping("/{id}")
	public ResponseEntity<Animal> findById(@PathVariable String id){
		Animal animal = service.findById(id);
		return ResponseEntity.ok().body(animal);
	}
	
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> insert(
		@RequestParam("name") String name, @RequestParam("age") Integer age,
		@RequestParam("weight") Double weight, @RequestParam("sex") Sex sex,
		@RequestParam("species") Species species, @RequestParam("size") Size size,
		@RequestParam("type") Type type,  @RequestParam("race") Race race,
		@RequestParam("description") String description, @RequestParam("image") MultipartFile image) {
		
	    try {
	    	Animal obj = service.saveAnimal(new Animal(name, age, weight, sex, species, size, type, race, description), image);
	        	        
	    	URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId())
					.toUri();

			return ResponseEntity.created(uri).build();
		} catch (IOException e) {
			System.out.print("Erro ao salvar animal: " + e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping("/{id}/imagem")
	public ResponseEntity<byte[]> getImagem(@PathVariable String id) {
		Animal animal = service.findById(id);

		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"image.jpg\"").body(animal.getImage());
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Void> update(@RequestParam String name, @RequestParam Integer age,
			@RequestParam Double weight, @RequestParam Sex sex,
			@RequestParam Species species, @RequestParam Size size,
			@RequestParam Type type,  @RequestParam Race race,
			@RequestParam String description, @PathVariable String id) {
		service.update(name, age, weight, sex, species, size, type, race, description, id);
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping("/{id}/medrecords")
	public ResponseEntity<Void> updateMedicalRecords(
			@RequestParam String diseases, 
			@RequestParam String medications,
			@PathVariable String id){
		
		List<PetDisease> diseasesList = service.enumListPetDisease(diseases); 
		List<PetMedication> medicationsList = service.enumListPetMedication(medications);
		
		service.updateMedicalRecords(diseasesList, medicationsList, id);
		return ResponseEntity.noContent().build();
	}
}