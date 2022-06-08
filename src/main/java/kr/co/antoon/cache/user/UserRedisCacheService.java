package kr.co.antoon.cache.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static kr.co.antoon.cache.user.UserRedisCacheService.UserKey.USER_KEY;

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
        return redisTemplate.opsForValue().get(USER_KEY.value + ":" + userId);
    }

    public void update(String key, String value, long expiredTime) {
        redisTemplate.opsForValue().set(USER_KEY.value + ":" + key, value, expiredTime, TimeUnit.MILLISECONDS);
    }

    public void delete(Long userId) {
        redisTemplate.delete(USER_KEY.value + ":" + userId);
    }
}