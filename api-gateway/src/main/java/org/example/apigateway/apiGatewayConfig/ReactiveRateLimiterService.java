package org.example.apigateway.apiGatewayConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class ReactiveRateLimiterService {

    @Value("${rateLimitConfig.maxRequest}")
    private int maxRequest;

    @Value("${rateLimitConfig.timeWindow}")
    private int timeWindow;


    private final ReactiveStringRedisTemplate redisTemplate;
    private final int MAX_REQUESTS = maxRequest;
    private final Duration TIME_WINDOW = Duration.ofSeconds(timeWindow);

    public ReactiveRateLimiterService(ReactiveStringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Mono<Boolean> isAllowed(String key) {
        String redisKey = "rate_limit:" + key;
        return redisTemplate.opsForValue().increment(redisKey)
                .flatMap(count -> {
                    if (count == 1) {
                        return redisTemplate.expire(redisKey, TIME_WINDOW).thenReturn(true);
                    }
                    return Mono.just(count <= MAX_REQUESTS);
                });
    }
}
