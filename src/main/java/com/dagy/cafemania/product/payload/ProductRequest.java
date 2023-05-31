package com.dagy.cafemania.product.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

@Builder
public record ProductRequest(
        String id,
        @NotNull(message = "Le nom du produit est obligatoire")
        @NotEmpty(message = "Le nom du produit est obligatoire")
        String name,
        @NotNull(message = "La description du produit est obligatoire")
        @NotEmpty(message = "La description du produit est obligatoire")
        String description,

        @NotNull(message = "Le prix du produit est obligatoire")
        @PositiveOrZero(message = "Le prix doit être superieur à zéro")
        Integer price
) {
}
