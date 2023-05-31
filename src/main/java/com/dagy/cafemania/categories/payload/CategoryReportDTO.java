package com.dagy.cafemania.categories.payload;

import lombok.*;

import java.time.LocalDateTime;

@Builder
public record CategoryReportDTO(
        Long id,
         String name,
        String description,
        Integer totalProducts,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {


}
