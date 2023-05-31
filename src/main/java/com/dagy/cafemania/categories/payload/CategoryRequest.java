package com.dagy.cafemania.categories.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder

public record CategoryRequest(
        String id,
        @NotNull(message = "Le nom de la categorie est obligatoire")
        @NotEmpty(message = "Le nom de la categorie est obligatoire")
        String name,
        @NotNull(message = "La description la categorie est obligatoire")
        @NotEmpty(message = "La description la categorie est obligatoire")
        String description
) {
}
