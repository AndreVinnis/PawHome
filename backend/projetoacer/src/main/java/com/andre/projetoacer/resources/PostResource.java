package com.andre.projetoacer.resources;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.andre.projetoacer.DTO.PostDTO;
import com.andre.projetoacer.domain.Animal;
import com.andre.projetoacer.domain.GenericUser;
import com.andre.projetoacer.domain.Post;
import com.andre.projetoacer.domain.User;
import com.andre.projetoacer.enums.Race;
import com.andre.projetoacer.enums.Sex;
import com.andre.projetoacer.enums.Size;
import com.andre.projetoacer.enums.Species;
import com.andre.projetoacer.enums.Type;
import com.andre.projetoacer.services.AnimalService;
import com.andre.projetoacer.services.PostService;
import com.andre.projetoacer.services.UserService;
import com.andre.projetoacer.util.PostUpdater;

@RestController
@RequestMapping(value="/posts")
public class PostResource {
	@Autowired
	private PostService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AnimalService animalService;
	
	@Autowired
	private PostUpdater postUpdater;
	
	
	@GetMapping
	public ResponseEntity<List<PostDTO>> findAll(){
		List<Post> list = service.findAll();
		List<PostDTO> listDTO = list.stream().map(x -> new PostDTO(x)).collect(Collectors.toList());	
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Post> findById(@PathVariable String id){
		Post post = service.findById(id);
		return ResponseEntity.ok().body(post);
	}
	
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> insert(
		@RequestParam("name") String name, @RequestParam("age") Integer age,
		@RequestParam("weight") Double weight, @RequestParam("sex") Sex sex,
		@RequestParam("species") Species species, @RequestParam("size") Size size,
		@RequestParam("type") Type type,  @RequestParam("race") Race race,
		@RequestParam("description") String description, @RequestParam("title") String title,
		@RequestParam("userId") String userId,
		@RequestParam("image") MultipartFile image) {
		
	    try {
	    	Animal animal = animalService.saveAnimal(new Animal(name, age, weight, sex, species, size, type, race, description), image);
	    	GenericUser user = userService.findById(userId);
	    	Post post = service.savePost(title, animal, user, image);
	        	        
	    	URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId())
					.toUri();
	    	
	    	postUpdater.updateListPosts(user, post);
	    	userService.updateListPosts((User) user);
			return ResponseEntity.created(uri).build();
		} catch (IOException e) {
			System.out.print("Erro ao salvar post: " + e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping("/{id}/imagem/user")
	public ResponseEntity<byte[]> getUserImagem(@PathVariable String id) {
		Post post = service.findById(id);

		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"image.jpg\"").body(post.getImageUser());
	}
	
	@GetMapping("/{id}/imagem/animal")
	public ResponseEntity<byte[]> getAnimalImagem(@PathVariable String id) {
		Post post = service.findById(id);

		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"image.jpg\"").body(post.getImageAnimal());
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody Post post, @PathVariable String id) {
		post = service.update(post, id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/strays")
	public ResponseEntity<List<PostDTO>> getStrays(){
		List<Post> list = service.getStrays();
		List<PostDTO> listDTO = list.stream().map(x -> new PostDTO(x)).collect(Collectors.toList());	
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping("/domestic")
	public ResponseEntity<List<PostDTO>> getDomesticAnimals(){
		List<Post> list = service.getDomesticAnimals();
		List<PostDTO> listDTO = list.stream().map(x -> new PostDTO(x)).collect(Collectors.toList());	
		return ResponseEntity.ok().body(listDTO);
	}
}
