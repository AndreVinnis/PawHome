package com.andre.projetoacer.resources;

import java.io.IOException;
import java.net.URI; 
import java.util.List;

//import javax.print.attribute.standard.Media;
//import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.MediaType;

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
    /*
    @PostMapping (consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> insert(@RequestBody User user) {
        user = service.insert(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
    */

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<Void> insert(
    @RequestParam("name") String name, @RequestParam("email") String email,
    @RequestParam("phoneNumber") String phoneNumber, @RequestParam("password") String password,
    @RequestParam("secondName") String secondName, @RequestParam("cpf") String cpf,
    @RequestParam("age") Integer age, @RequestParam("image") MultipartFile image,
    @RequestParam("adress") String adress 
) {
    try {
        // Crie/adapte o objeto Adress conforme necessário
        Adress adressObj = new Adress(adress, adress, adress, age, adress);

        User user = new User(name, email, phoneNumber, password, adressObj, secondName, cpf, age, image.getBytes());
        user = service.insert(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).build();
    } catch (IOException e) {
        System.out.print("Erro ao salvar usuário: " + e.getMessage());
        return ResponseEntity.internalServerError().build();
    }
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
     return ResponseEntity.noContent().build();
    }
}
