package com.example.stark_group_vms.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record PagedResponse<T>(
        List<T> content,
        boolean hasNext,
        long totalElements
) {
    public static <T> PagedResponse<T> from(Page<T> page) {
        return new PagedResponse<>(
                page.getContent(),
                page.hasNext(),
                page.getTotalElements()
        );
    }
}

