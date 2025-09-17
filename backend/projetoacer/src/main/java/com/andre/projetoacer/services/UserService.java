package com.andre.projetoacer.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
//import com.andre.projetoacer.domain.GenericUser;
import com.andre.projetoacer.domain.User;

import com.andre.projetoacer.repository.UserRepository;
import com.andre.projetoacer.services.exception.ObjectNotFoundException;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    public List<User> findAll(){
        return repository.findAll();
    }

    public User findById(String id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("User not found"));
    }
    
    public User saveUser(User user, MultipartFile image) throws IOException {
	    user.setImagem(image.getBytes());
	    return repository.save(user);
	}

    public User updateListPosts(User user) {
        return repository.save(user);
    }   

    public User update(String id, User newUser) {
    User originalUser = repository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("User not found"));

    partialUpdate(originalUser, newUser);
    return repository.save(originalUser);
    }

    private void partialUpdate(User originalUser, User newUser) {
        if (newUser.getName() != null) {
            originalUser.setName(newUser.getName());
        }
        if (newUser.getEmail() != null) {
            originalUser.setEmail(newUser.getEmail());
        }
        if (newUser.getPhoneNumber() != null) {
            originalUser.setPhoneNumber(newUser.getPhoneNumber());
        }
        if (newUser.getPassword() != null) {
            originalUser.setPassword(newUser.getPassword());
        }
        if (newUser.getAdress() != null) {
            originalUser.setAdress(newUser.getAdress());
        }
        if (newUser.getSecondName() != null) {
            originalUser.setSecondName(newUser.getSecondName());
        }
        if (newUser.getCpf() != null) {
            originalUser.setCpf(newUser.getCpf());
        }
        if (newUser.getBirthDate() != null) {
            originalUser.setBirthDate(newUser.getBirthDate());
        }
    }

    public void delete(String id) {
	    repository.findById(id)
	        .orElseThrow(() -> new ObjectNotFoundException("User not found"));
	    repository.deleteById(id);
    }

}
