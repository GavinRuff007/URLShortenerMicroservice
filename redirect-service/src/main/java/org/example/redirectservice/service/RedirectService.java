package org.example.redirectservice.service;

import org.example.redirectservice.exception.UrlNotFoundException;
import org.example.redirectservice.model.UrlMapping;
import org.example.redirectservice.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.data.redis.timeUnit}")
    private int timeUnit;

    @Value("${spring.data.redis.timeUnitType}")
    private String timeUnitType;

    public String getOriginalUrl(String shortUrl) {

        TimeUnit timeUnit1 = TimeUnit.MINUTES;
        if(timeUnitType.equals("SECONDS")){
            timeUnit1 = TimeUnit.SECONDS;
        }
        
        String redisKey = "short:" + shortUrl;

        String cachedUrl = redisTemplate.opsForValue().get(redisKey);
        if (cachedUrl != null) {
            return cachedUrl;
        }

        UrlMapping mapping = repository.findByShortCode(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException("Short URL not found"));

        redisTemplate.opsForValue().set(redisKey, mapping.getOriginalUrl(), timeUnit, timeUnit1);

        return mapping.getOriginalUrl();
    }
}
