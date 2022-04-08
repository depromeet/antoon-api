package kr.co.antoon.common.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.List;

@Getter
public class PageDto<T> implements Serializable {
    private final List<T> data;
    private final int page;
    private final int size;
    private final int totalPages;
    private final long totalElements;
    private final boolean firstPage;
    private final boolean lastPage;

    private PageDto(final Page<T> page) {
        this.data = page.getContent();
        this.page = page.getNumber();
        this.size = page.getSize();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.firstPage = page.isFirst();
        this.lastPage = page.isLast();
    }

    public static <T> ResponseEntity<PageDto<T>> ok(final Page<T> page) {
        return ResponseEntity.ok(new PageDto<>(page));
    }
}