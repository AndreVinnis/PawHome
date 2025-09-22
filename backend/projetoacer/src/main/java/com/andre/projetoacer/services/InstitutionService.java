package com.andre.projetoacer.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.andre.projetoacer.domain.Institution;
//import com.andre.projetoacer.domain.User;
import com.andre.projetoacer.repository.InstitutionRepository;
import com.andre.projetoacer.services.exception.ObjectNotFoundException;

@Service
public class InstitutionService {
    @Autowired
    private InstitutionRepository repository;
    public List<Institution> findAll(){
        return repository.findAll();
    }

    public Institution findById(String id) {
        Optional<Institution> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Institution not found"));
    } 

    public Institution saveInstitution(Institution institution, MultipartFile image) throws Exception {
        /* 
        if (repository.existsByEmail(institution.getEmail())) {
            throw new IllegalArgumentException("E=mail already registered");
        }
        */
        institution.setImagem(image.getBytes());
        return repository.save(institution);
    }

    public Institution updateListPosts(Institution institution) {
        return repository.save(institution);
    }

    public Institution update(String id, Institution newInstitution) {
    Institution originalInstitution = repository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("Institution not found"));

    partialUpdate(originalInstitution, newInstitution);
    return repository.save(originalInstitution);
    }

    private void partialUpdate(Institution originalInstitution, Institution newInstitution) {
        if (newInstitution.getName() != null) {
            originalInstitution.setName(newInstitution.getName());
        }
        if (newInstitution.getEmail() != null) {
            originalInstitution.setEmail(newInstitution.getEmail());
        }
        if (newInstitution.getPhoneNumber() != null) {
            originalInstitution.setPhoneNumber(newInstitution.getPhoneNumber());
        }
        if (newInstitution.getPassword() != null) {
            originalInstitution.setPassword(newInstitution.getPassword());
        }
        if (newInstitution.getAdress() != null) {
            originalInstitution.setAdress(newInstitution.getAdress());
        }
        if (newInstitution.getCnpj() != null) {
            originalInstitution.setCnpj(newInstitution.getCnpj());
        }
        if (newInstitution.getDescription() != null) {
            originalInstitution.setDescription(newInstitution.getDescription());
        }
        if (newInstitution.getCreateDate() != null) {
            originalInstitution.setCreateDate(newInstitution.getCreateDate());
        }

    }

    public void delete(String id) {
        repository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Institution not found"));
        repository.deleteById(id);
    }

    public Institution updateImage(String id, MultipartFile image) throws Exception {
        Institution institution = findById(id);
        institution.setImagem(image.getBytes());
        return repository.save(institution);
    }
    
}
