package com.andre.projetoacer.resources;

import java.util.LinkedList;
import java.util.List;
import com.andre.projetoacer.DTO.MessageResponse;
import com.andre.projetoacer.DTO.PostDTO;
import com.andre.projetoacer.DTO.institution.InstitutionCreationDTO;
import com.andre.projetoacer.domain.Post;
import com.andre.projetoacer.enums.UserRole;
import com.andre.projetoacer.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.andre.projetoacer.domain.Address;
import com.andre.projetoacer.domain.Institution;
import com.andre.projetoacer.services.InstitutionService;

@RestController
@RequestMapping(value="/institutions")
public class InstitutionResource {

    @Autowired
    private InstitutionService service;

    @GetMapping
    public ResponseEntity<List<Institution>> findAll() {
        List<Institution> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Institution> findById(@PathVariable String id){
        Institution institution = service.findById(id);
        return ResponseEntity.ok().body(institution);
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<PostDTO>> findInstitutionPosts(@PathVariable String id){
        Institution institution = service.findById(id);
        List<PostDTO> posts = new LinkedList<>();
        for(Post post : institution.getPosts()){
            posts.add(new PostDTO(post));
        }
        return ResponseEntity.ok().body(posts);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> insert(@RequestBody InstitutionCreationDTO newInstitution) {
        try{
            service.findByEmail(newInstitution.email());
            service.findByCnpj(newInstitution.cnpj());
            return ResponseEntity.badRequest().body(new MessageResponse("Já existe um usuário com esse email."));
        }catch(ObjectNotFoundException ex){
            service.saveInstitution(newInstitution);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Instituição criada com sucesso!"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/imagem")
    public ResponseEntity<byte[]> getImagem(@PathVariable String id) {
        Institution institution = service.findById(id);
        if(institution.getImage() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_JPEG)
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"image.jpg\"")
            .body(institution.getImage());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(
        @PathVariable String id, @RequestParam String name,
        @RequestParam String email, @RequestParam String phoneNumber,
        @RequestParam String password, @RequestParam String cnpj,
        @RequestParam String description, @RequestParam String cep,
        @RequestParam String city, @RequestParam String neighborhood,
        @RequestParam Integer houseNumber, @RequestParam String referencePoint) {

        Address addressObj = new Address(cep, city, neighborhood, houseNumber, referencePoint);
        Institution institution = new Institution(name, email, phoneNumber, password, addressObj, cnpj, description, null, UserRole.USER);
        institution.setId(id);
        service.update(id, institution);
        return ResponseEntity.noContent().build();
    }
}
