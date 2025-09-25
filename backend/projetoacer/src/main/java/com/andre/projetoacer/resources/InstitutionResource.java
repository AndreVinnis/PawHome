package com.andre.projetoacer.resources;

import java.net.URI;
import java.sql.Date;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> insert(
        @RequestParam("name") String name, @RequestParam("email") String email,
        @RequestParam("phoneNumber") String phoneNumber, @RequestParam("password") String password,
        @RequestParam("cnpj") String cnpj, @RequestParam("description") String description,
        @RequestParam("image") MultipartFile image,@RequestParam("cep") String cep, @RequestParam("city") String city,
        @RequestParam("neighborhood") Date neighborhood, @RequestParam("houseNumber") Integer houseNumber,
        @RequestParam("referencePoint") String referencePoint
    ) {
        try {
            Address addressObj = new Address();

            Institution institution = new Institution(name, email, phoneNumber, password, addressObj, cnpj, description, null);
            institution = service.saveInstitution(institution, image);

            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(institution.getId()).toUri();
            return ResponseEntity.created(uri).build();
        } catch (Exception e) {
            System.out.println("Error saving institution: " + e.getMessage());
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
        @RequestParam String city, @RequestParam Date neighborhood,
        @RequestParam Integer houseNumber, @RequestParam String referencePoint) {

        Address addressObj = new Address();
        Institution institution = new Institution(name, email, phoneNumber, password, addressObj, cnpj, description, null);
        institution.setId(id);
        service.update(id, institution);
        return ResponseEntity.noContent().build();
    }

}
