package kr.co.antoon.cache.webtoon;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebtoonRedisCacheService {
    private final RedisTemplate<String, String> redisTemplate;

    public void evict(WebtoonRedisKey key) {
        redisTemplate.delete(key.getValue());
    }
}
