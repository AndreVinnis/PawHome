package com.andre.projetoacer.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.andre.projetoacer.DTO.user.UserCreationDTO;
import com.andre.projetoacer.domain.Address;
import com.andre.projetoacer.domain.GenericUser;
import com.andre.projetoacer.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.andre.projetoacer.domain.User;
import com.andre.projetoacer.repository.UserRepository;
import com.andre.projetoacer.services.exception.ObjectNotFoundException;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    public List<User> findAll(){
        return repository.findAll();
    }

    public User findById(String id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("User not found"));
    }

    public User findByEmail(String email) {
        User obj = repository.findByEmail(email);
        if(obj == null) throw new ObjectNotFoundException("User not found");
        return obj;
    }

    public User saveUser(UserCreationDTO user, UserRole role) {
        User existingUser = repository.findByEmail(user.email());

        if (existingUser != null) {
            throw new RuntimeException("E-mail já cadastrado no sistema.");
        }

        Address address = new Address(user.cep(), user.city(), user.neighborhood(), user.number(), user.referencePoint());
        User newUser = new User(user.name(), user.email(), user.phoneNumber(), encoder.encode(user.password()), address, user.secondName(), user.cpf(), user.birthDate(), role);
        return repository.save(newUser);
    }

    public User update(String id, UserCreationDTO newUser) {
        User originalUser = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("User not found"));
        partialUpdate(originalUser, newUser);
        return repository.save(originalUser);
    }

    public void uploadUserImage(String id, MultipartFile file) {
        try {
            User user = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));
            byte[] bytes = file.getBytes();
            user.setImage(bytes);
            repository.save(user);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar a imagem");
        }
    }

    private void partialUpdate(User originalUser, UserCreationDTO newUser) {
        if (newUser.name() != null) {
            originalUser.setName(newUser.name());
        }
        if (newUser.email() != null) {
            originalUser.setEmail(newUser.email());
        }
        if (newUser.phoneNumber() != null) {
            originalUser.setPhoneNumber(newUser.phoneNumber());
        }
        if (newUser.password() != null) {
            originalUser.setPassword(encoder.encode(newUser.password()));
        }
        if (newUser.cep() != null
            || newUser.city() != null
            || newUser.neighborhood() != null
            || newUser.number() != null
            || newUser.referencePoint() != null) {
            Address address = new Address(newUser.cep(), newUser.city(), newUser.neighborhood(),  newUser.number(), newUser.referencePoint());
            originalUser.setAddress(address);
        }
        if (newUser.secondName() != null) {
            originalUser.setSecondName(newUser.secondName());
        }
        if (newUser.cpf() != null) {
            originalUser.setCpf(newUser.cpf());
        }
        if (newUser.birthDate() != null) {
            originalUser.setBirthDate(newUser.birthDate());
        }
    }

    public void delete(String id) {
	    repository.findById(id)
	        .orElseThrow(() -> new ObjectNotFoundException("User not found"));
	    repository.deleteById(id);
    }
}
