package kr.co.antoon.cache.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserRedisCacheService {
    @Getter
    @RequiredArgsConstructor
    enum UserKey {
        USER_KEY("auth:user"),
        ;

        private final String value;
    }

    private final RedisTemplate<String, String> redisTemplate;

    public String get(String token) {
        return redisTemplate.opsForValue().get(token);
    }

    public String get(Long userId) {
        return redisTemplate.opsForValue().get(UserKey.USER_KEY.value + ":" + userId);
    }

    public void update(String key, String value, long expiredTime) {
        redisTemplate.opsForValue().set(UserKey.USER_KEY.value + ":" + key, value, expiredTime, TimeUnit.MILLISECONDS);
    }

    public void delete(Long userId) {
        redisTemplate.delete(UserKey.USER_KEY.value + ":" + userId);
    }
}