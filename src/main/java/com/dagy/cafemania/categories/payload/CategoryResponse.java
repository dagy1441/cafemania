package com.dagy.cafemania.categories.payload;

import com.dagy.cafemania.product.payload.ProductResponse;
import lombok.Builder;

import java.util.Set;

@Builder
public record CategoryResponse(
        String id,
        String name,
        String description,
        Set<ProductResponse> products
) {
}
