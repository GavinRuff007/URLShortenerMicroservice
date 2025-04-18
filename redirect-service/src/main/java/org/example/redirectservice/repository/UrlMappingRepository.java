package org.example.redirectservice.repository;

import org.example.redirectservice.model.UrlMapping;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UrlMappingRepository extends MongoRepository<UrlMapping, String> {

    Optional<UrlMapping> findByShortCode(String shortCode);

}
