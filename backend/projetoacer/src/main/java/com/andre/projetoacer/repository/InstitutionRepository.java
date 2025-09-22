package com.andre.projetoacer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.andre.projetoacer.domain.Institution;

@Repository
public interface InstitutionRepository extends MongoRepository<Institution, String> {
    
}