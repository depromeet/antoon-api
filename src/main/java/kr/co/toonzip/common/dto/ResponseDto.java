package kr.co.toonzip.common.dto;

import org.springframework.http.ResponseEntity;

import java.io.Serializable;

public record ResponseDto<T>(T date) implements Serializable {
    public static <T> ResponseEntity<T> success(T data) {
        return ResponseEntity.ok(data);
    }
}