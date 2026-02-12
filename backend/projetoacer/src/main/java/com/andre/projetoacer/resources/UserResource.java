package com.andre.projetoacer.resources;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.andre.projetoacer.DTO.MessageResponse;
import com.andre.projetoacer.DTO.user.UserCreationDTO;
import com.andre.projetoacer.enums.UserRole;
import com.andre.projetoacer.services.exception.ObjectNotFoundException;
import org.springframework.http.HttpHeaders;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.andre.projetoacer.domain.Address;
import com.andre.projetoacer.domain.User;
import com.andre.projetoacer.services.UserService;
import org.springframework.web.multipart.MultipartFile;

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
    
	@PostMapping("/normal")
	public ResponseEntity<MessageResponse> insertNormalUser(@RequestBody UserCreationDTO newUser) {
        try{
            service.findByEmail(newUser.email());
            return ResponseEntity.badRequest().body(new MessageResponse("Já existe um usuário com esse email."));
        }
        catch(ObjectNotFoundException ex){
            service.saveUser(newUser, UserRole.USER);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Usuário criado com sucesso!"));
        }
	}

    @PostMapping("/admin")
    public ResponseEntity<MessageResponse> insertAdminUser(@RequestBody UserCreationDTO newUser) {
        try{
            service.findByEmail(newUser.email());
            return ResponseEntity.badRequest().body(new MessageResponse("Já existe um usuário com esse email."));
        }
        catch(ObjectNotFoundException ex){
            service.saveUser(newUser, UserRole.ADMIN);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Usuário criado com sucesso!"));
        }
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<Void> uploadImage(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        service.uploadUserImage(id, file);
        return ResponseEntity.noContent().build();
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
        Address addressObj = new Address(cep, city, neighborhood, houseNumber, referencePoint);
        User user = new User(name, email, phoneNumber, password, addressObj, secondName, cpf, birthDate, UserRole.USER);
        user.setId(id);
        service.update(id, user);
        return ResponseEntity.noContent().build();
        
    }
}


