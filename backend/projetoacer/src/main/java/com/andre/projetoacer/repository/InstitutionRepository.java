package com.andre.projetoacer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.andre.projetoacer.domain.Institution;
import java.util.Optional;

@Repository
public interface InstitutionRepository extends MongoRepository<Institution, String> {
    Institution findByEmail(String email);

    Optional<Institution> findByCnpj(String cnpj);

}