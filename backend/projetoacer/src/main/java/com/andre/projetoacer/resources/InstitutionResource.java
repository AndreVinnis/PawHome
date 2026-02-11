package com.andre.projetoacer.resources;

import java.net.URI;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.andre.projetoacer.DTO.PostDTO;
import com.andre.projetoacer.domain.Post;
import com.andre.projetoacer.enums.UserRole;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> insert(
        @RequestParam("name") String name, @RequestParam("email") String email,
        @RequestParam("phoneNumber") String phoneNumber, @RequestParam("password") String password,
        @RequestParam("cnpj") String cnpj, @RequestParam("description") String description,
        @RequestParam("image") MultipartFile image,@RequestParam("cep") String cep, @RequestParam("city") String city,
        @RequestParam("neighborhood") String neighborhood, @RequestParam("houseNumber") Integer houseNumber,
        @RequestParam("referencePoint") String referencePoint
    ) {
         Address addressObj = new Address(cep, city, neighborhood, houseNumber, referencePoint);

         Institution institution = new Institution(name, email, phoneNumber, password, addressObj, cnpj, description, new Date(), UserRole.USER);
         institution = service.saveInstitution(institution, image);
         URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(institution.getId()).toUri();
         return ResponseEntity.created(uri).build();
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
