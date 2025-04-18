package org.example.shortenerservice.service;

import lombok.RequiredArgsConstructor;
import org.example.shortenerservice.model.UrlMapping;
import org.example.shortenerservice.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UrlShortenerService {

    private final UrlMappingRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${mongodb.expiryhours}")
    private int expiryhours;

    public String shortenUrl(String originalUrl) {
        String shortCode = generateUniqueCode();

        Instant expiryInstant = Instant.now().plus(expiryhours, ChronoUnit.HOURS);
        Date expiryDate = Date.from(expiryInstant);

        Date createdAt = Date.from(Instant.now());

        UrlMapping urlMapping = UrlMapping.builder()
                .shortCode(shortCode)
                .originalUrl(originalUrl)
                .expiryDate(expiryDate)
                .createdAt(createdAt)
                .build();

        repository.save(urlMapping);
        Map<String,String> stringStringMap = new HashMap<>();
        stringStringMap.put("shortCode", shortCode);
        stringStringMap.put("originalUrl", originalUrl);

        kafkaTemplate.send("url-created-topic", stringStringMap.toString());

        return "http://localhost:8080/r/"+shortCode;

    }

    private String generateUniqueCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            code.append(characters.charAt(index));
        }
        return code.toString();
    }
}
