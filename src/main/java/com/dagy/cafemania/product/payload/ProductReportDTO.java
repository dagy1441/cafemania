package com.dagy.cafemania.product.payload;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ProductReportDTO(
         String name,
         String description,
         String price,
         String categoryName,
         LocalDateTime createdAt,
         LocalDateTime updatedAt
) {
}
