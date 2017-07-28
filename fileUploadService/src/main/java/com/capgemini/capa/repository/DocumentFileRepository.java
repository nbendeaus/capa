package com.capgemini.capa.repository;

import com.capgemini.capa.domain.DocumentFile;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the DocumentFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentFileRepository extends MongoRepository<DocumentFile,String> {
    
}
