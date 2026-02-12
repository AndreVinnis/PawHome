package com.andre.projetoacer.services;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.andre.projetoacer.DTO.institution.InstitutionCreationDTO;
import com.andre.projetoacer.domain.Address;
import com.andre.projetoacer.domain.User;
import com.andre.projetoacer.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.andre.projetoacer.domain.Institution;
import com.andre.projetoacer.repository.InstitutionRepository;
import com.andre.projetoacer.services.exception.ObjectNotFoundException;

@Service
public class InstitutionService {

    @Autowired
    private InstitutionRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    public List<Institution> findAll(){
        return repository.findAll();
    }

    public Institution findById(String id) {
        Optional<Institution> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Institution not found"));
    }

    public Institution findByEmail(String email) {
        Institution obj = repository.findByEmail(email);
        if(obj == null) throw new ObjectNotFoundException("Institution not found");
        return obj;
    }

    public Institution findByCnpj(String cnpj) {
        Optional<Institution> obj = repository.findByCnpj(cnpj);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Institution not found"));
    }

    public Institution saveInstitution(InstitutionCreationDTO institution) {
        Address address = new Address(institution.cep(), institution.city(), institution.neighborhood(), institution.number(), institution.referencePoint());
        Institution newInstitution = new Institution(institution.name(), institution.email(), institution.phoneNumber(),
                encoder.encode(institution.password()), address, institution.cnpj(), institution.description(), new Date(), UserRole.USER);
        return repository.save(newInstitution);
    }

    public void uploadUserImage(String id, MultipartFile file) {
        try {
            Institution institution = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));
            byte[] bytes = file.getBytes();
            institution.setImagem(bytes);
            repository.save(institution);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar a imagem");
        }
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
        if (newInstitution.getAddress() != null) {
            originalInstitution.setAddress(newInstitution.getAddress());
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
}
