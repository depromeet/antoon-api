package kr.co.antoon.common.dto;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.List;

public record PageDto<T>(
        List<T> data,
        int page,
        int size,
        int totalPages,
        long totalElements,
        boolean firstPage,
        boolean lastPage
) implements Serializable {
    private PageDto(final Page<T> page) {
        this(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.isFirst(),
                page.isLast()
        );
    }

    public static <T> ResponseEntity<PageDto<T>> ok(final Page<T> page) {
        return ResponseEntity.ok(new PageDto<>(page));
    }
}
