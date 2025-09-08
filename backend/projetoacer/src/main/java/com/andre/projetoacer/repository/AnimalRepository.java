package com.andre.projetoacer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.andre.projetoacer.domain.Animal;

@Repository
public interface AnimalRepository extends MongoRepository<Animal, Integer>{

}
