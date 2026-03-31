package com.andre.projetoacer.services;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.andre.projetoacer.DTO.institution.InstitutionCreationDTO;
import com.andre.projetoacer.domain.Address;
import com.andre.projetoacer.domain.User;
import com.andre.projetoacer.enums.UserRole;
import com.andre.projetoacer.services.exception.IncorrectInputValues;
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
        return obj.orElseThrow(() -> new ObjectNotFoundException("Institution not found!"));
    }

    public Institution findByEmail(String email) {
        Institution obj = repository.findByEmail(email);
        if(obj == null) throw new ObjectNotFoundException("Institution not found!");
        return obj;
    }

    public Institution findByCnpj(String cnpj) {
        Optional<Institution> obj = repository.findByCnpj(cnpj);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Institution not found!"));
    }

    public Institution saveInstitution(InstitutionCreationDTO institution) {
        Institution existEmailInstitution = repository.findByEmail(institution.email());
        Optional<Institution> existCpnjInstitution = repository.findByCnpj(institution.cnpj());

        if(existEmailInstitution != null){
            throw new IncorrectInputValues("E-mail já cadastrado no sistema!");
        }
        if(existCpnjInstitution.isPresent()){
            throw new IncorrectInputValues("CNPJ já cadastrado no sistema!");
        }

        Address address = new Address(institution.cep(), institution.city(), institution.neighborhood(), institution.number(), institution.referencePoint());
        Institution newInstitution = new Institution(institution.name(), institution.email(), institution.phoneNumber(),
                encoder.encode(institution.password()), address, institution.cnpj(), institution.description(), new Date(), UserRole.USER);
        return repository.save(newInstitution);
    }

    public void uploadInstitutionImage(String id, MultipartFile file) {
        try {
            Institution institution = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado!"));
            byte[] bytes = file.getBytes();
            institution.setImage(bytes);
            repository.save(institution);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar a imagem!");
        }
    }

    public void updateListPosts(Institution institution){
        repository.save(institution);
    }

    public Institution update(String id, InstitutionCreationDTO newInstitution) {
        Institution originalInstitution = repository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Institution not found!"));

        partialUpdate(originalInstitution, newInstitution);
        return repository.save(originalInstitution);
    }

    private void partialUpdate(Institution originalInstitution, InstitutionCreationDTO newInstitution) {
        if (newInstitution.name() != null) {
            originalInstitution.setName(newInstitution.name());
        }
        if (newInstitution.email() != null) {
            originalInstitution.setEmail(newInstitution.email());
        }
        if (newInstitution.phoneNumber() != null) {
            originalInstitution.setPhoneNumber(newInstitution.phoneNumber());
        }
        if (newInstitution.password() != null) {
            originalInstitution.setPassword(encoder.encode(newInstitution.password()));
        }
        if (newInstitution.cep() != null
                || newInstitution.city() != null
                || newInstitution.neighborhood() != null
                || newInstitution.number() != null
                || newInstitution.referencePoint() != null) {
            Address address = new Address(newInstitution.cep(), newInstitution.city(), newInstitution.neighborhood(),  newInstitution.number(), newInstitution.referencePoint());
            originalInstitution.setAddress(address);
        }
        if (newInstitution.cnpj() != null) {
            originalInstitution.setCnpj(newInstitution.cnpj());
        }
        if (newInstitution.description() != null) {
            originalInstitution.setDescription(newInstitution.description());
        }
    }

    public void delete(String id) {
        repository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Institution not found!"));
        repository.deleteById(id);
    }
}
