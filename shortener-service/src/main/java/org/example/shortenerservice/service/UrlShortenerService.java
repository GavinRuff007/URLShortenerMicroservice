package org.example.shortenerservice.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import org.example.shortenerservice.model.UrlMapping;
import org.example.shortenerservice.repository.UrlMappingRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UrlShortenerService {

    private final UrlMappingRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final int SHORT_CODE_LENGTH = 6;
    private static final int EXPIRY_DAYS = 7; // مدت انقضا پیش‌فرض

    // تابع برای کوتاه کردن URL
    public String shortenUrl(String originalUrl) {
        String shortCode = generateUniqueCode();

        // تاریخ انقضا 7 روز پس از ایجاد
        Instant expiryDate = Instant.now().plus(EXPIRY_DAYS, ChronoUnit.DAYS);

        // ساخت و ذخیره لینک کوتاه
        UrlMapping urlMapping = UrlMapping.builder()
                .shortCode(shortCode)
                .originalUrl(originalUrl)
                .expiryDate(expiryDate)
                .createdAt(Instant.now())
                .build();

        repository.save(urlMapping);

        // ارسال پیام به Kafka برای این لینک ایجاد شده
        kafkaTemplate.send("url-created-topic", shortCode);

        return shortCode;
    }

    // تابع برای تولید کد کوتاه یکتا
    private String generateUniqueCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < SHORT_CODE_LENGTH; i++) {
            int index = random.nextInt(characters.length());
            code.append(characters.charAt(index));
        }
        return code.toString();
    }
}
