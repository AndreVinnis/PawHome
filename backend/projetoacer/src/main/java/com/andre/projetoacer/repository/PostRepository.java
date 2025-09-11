package com.andre.projetoacer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.andre.projetoacer.domain.Post;

@Repository
public interface PostRepository extends MongoRepository<Post, String>{

}