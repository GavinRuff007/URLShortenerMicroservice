package org.example.cachingservice.event;

import org.example.cachingservice.dto.UrlCreatedEvent;
import org.example.cachingservice.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class UrlKafkaListener {

    private final StringRedisTemplate redisTemplate;


    private static final Logger log = LoggerFactory.getLogger(UrlKafkaListener.class);


    private final RedisService redisService;

    public  UrlKafkaListener(StringRedisTemplate redisTemplate, RedisService redisService){
        this.redisTemplate = redisTemplate;
        this.redisService = redisService;
    }

    @KafkaListener(topics = "url-created-topic", groupId = "test-group")
    public void listen(UrlCreatedEvent event) {

        try {
            String[] parts = event.toString().split("\\|", 2);
            String shortCode = parts[0];
            String originalUrl = parts[1];
            redisService.save(event.getShortUrl(), event.getOriginalUrl(), 5);
            log.info("✅ URL cached in Redis - shortCode: {}, url: {}", shortCode, originalUrl);
        } catch (Exception e) {
            log.error("❌ Failed to cache URL in Redis: {}", event.toString(), e);
        }
    }


}
