package com.dagy.cafemania.categories.payload;

import com.dagy.cafemania.shared.utilities.pagination.SortItem;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
public class CategorySearchRequest {


    private String name;

    private String description;


    @NotNull(message = "page cannot be null")
    @PositiveOrZero(message = "page must be a zero or a positive number")
    private Integer page;

    @Schema(example = "10")
    @NotNull(message = "size cannot be null")
    @Positive(message = "size must be a positive number")
    private Integer size;

    private List<SortItem> sortList;

    public CategorySearchRequest(int page, int size, List<SortItem> sortList) {
    }
}
