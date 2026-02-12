package com.andre.projetoacer.resources;

import java.util.List;
import java.util.stream.Collectors;
import com.andre.projetoacer.DTO.MessageResponse;
import com.andre.projetoacer.DTO.post.PostCreationDTO;
import com.andre.projetoacer.domain.*;
import com.andre.projetoacer.services.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.andre.projetoacer.DTO.post.PostDTO;
import com.andre.projetoacer.services.PostService;
import com.andre.projetoacer.services.UserService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value="/posts")
public class PostResource {
	@Autowired
	private PostService service;
	
	@Autowired
	private UserService userService;

    @Autowired
    private InstitutionService institutionService;
	
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
	
	@PostMapping
	public ResponseEntity<MessageResponse> insert(@RequestBody PostCreationDTO newPost, @AuthenticationPrincipal UserDetails userDetails) {
        service.savePost(newPost, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Post criado com sucesso!"));
	}

    @PostMapping("/{id}/image")
    public ResponseEntity<Void> uploadImage(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        service.uploadAnimalImage(id, file);
        return ResponseEntity.noContent().build();
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
