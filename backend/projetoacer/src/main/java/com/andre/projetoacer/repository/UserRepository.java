package com.andre.projetoacer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.andre.projetoacer.domain.User;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String>{
    User findByEmail(String email);
}