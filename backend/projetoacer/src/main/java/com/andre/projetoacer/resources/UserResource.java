package com.andre.projetoacer.resources;

import java.io.IOException;
import java.net.URI;
import org.springframework.http.HttpHeaders;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.andre.projetoacer.domain.Adress;
import com.andre.projetoacer.domain.User;
import com.andre.projetoacer.services.UserService;

@RestController
@RequestMapping(value="/users")
public class UserResource {

    @Autowired
    private UserService service;
    

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable String id) {
        User user = service.findById(id);
        return ResponseEntity.ok().body(user);
    }
    
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> insert(
	    @RequestParam("name") String name, @RequestParam("email") String email,
	    @RequestParam("phoneNumber") String phoneNumber, @RequestParam("password") String password,
	    @RequestParam("secondName") String secondName, @RequestParam("cpf") String cpf,
	    @RequestParam("birthDate") Date birthDate, @RequestParam("image") MultipartFile image,
	    @RequestParam("cep") String cep, @RequestParam("city") String city,
	    @RequestParam("neighborhood") String neighborhood, @RequestParam("houseNumber") Integer houseNumber,
	    @RequestParam("referencePoint") String referencePoint
	) {
	    try {
	        Adress adressObj = new Adress(cep, city, neighborhood, houseNumber, referencePoint);
	
	        User user = new User(name, email, phoneNumber, password, adressObj, secondName, cpf, birthDate);
	        user = service.saveUser(user, null);
	
	        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
	        return ResponseEntity.created(uri).build();
	    } catch (IOException e) {
	        System.out.print("Error saving user: " + e.getMessage());
	        return ResponseEntity.internalServerError().build();
	    }
	}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
     return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/imagem")
    public ResponseEntity<byte[]> getImagem(@PathVariable String id) {
        User user = service.findById(id);
        if (user.getImage() == null) {
            return ResponseEntity.notFound().build();
        }   
        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_JPEG) 
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"image.jpg\"")
            .body(user.getImage());
    } 

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable String id, @RequestParam String name,
            @RequestParam String email, @RequestParam String phoneNumber,
            @RequestParam String password, @RequestParam String secondName,
            @RequestParam String cpf, @RequestParam Date birthDate,
            @RequestParam String cep, @RequestParam String city,
            @RequestParam String neighborhood, @RequestParam Integer houseNumber,
            @RequestParam String referencePoint) {
        Adress adressObj = new Adress(cep, city, neighborhood, houseNumber, referencePoint);
        User user = new User(name, email, phoneNumber, password, adressObj, secondName, cpf, birthDate);
        user.setId(id);
        service.update(id, user);
        return ResponseEntity.noContent().build();
        
    }
}


