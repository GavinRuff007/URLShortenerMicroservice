package org.example.redirectservice.service;

import org.example.redirectservice.model.UrlMapping;
import org.example.redirectservice.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.TimeUnit;

@Service
public class RedirectService {

    @Autowired
    private UrlMappingRepository repository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public String getOriginalUrl(String shortUrl) {
        String redisKey = "short:" + shortUrl;

        String cachedUrl = redisTemplate.opsForValue().get(redisKey);
        if (cachedUrl != null) {
            return cachedUrl;
        }

        UrlMapping mapping = repository.findByShortCode(shortUrl)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Short URL not found"));

        redisTemplate.opsForValue().set(redisKey, mapping.getOriginalUrl(), 1, TimeUnit.HOURS);

        return mapping.getOriginalUrl();
    }
}
