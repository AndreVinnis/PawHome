package com.andre.projetoacer.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.andre.projetoacer.domain.GenericUser;
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

    public User insert(User user) {
        return repository.save(user);
    }   

    public User update(String id, User user) {
        user.setId(id);
        return repository.save(user);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
    /*
    public User saveUser(User user, MultipartFile image) throws IOException {
    user.setImage(image.getBytes());
    return repository.save(user);
	}
	*/
}
