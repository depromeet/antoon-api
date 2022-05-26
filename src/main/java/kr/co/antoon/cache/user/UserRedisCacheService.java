package kr.co.antoon.cache.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserRedisCacheService {
    private final static String USER_REDIS_KEY = "auth:user:"; // TODO : 해당 키를 사용해주세요!
    private final RedisTemplate<String, String> redisTemplate;

    public String get(Long userId) {
        return redisTemplate.opsForValue().get(USER_REDIS_KEY + userId);
    }

    public void update(
            String key,
            String value,
            long expiredTime
    ) {
        redisTemplate.opsForValue().set(USER_REDIS_KEY + key, value, expiredTime, TimeUnit.MILLISECONDS);
    }

    public void delete(Long userId) {
        redisTemplate.delete("RT: " + userId);
    }
}