package com.dagy.cafemania.categories.payload;

import com.dagy.cafemania.categories.Category;
import com.dagy.cafemania.product.payload.ProductMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface CategoryMapper {

    Category mapCategoryResponseToCategory(CategoryResponse categoryResponse);
    CategoryResponse mapCategoryToCategoryResponse(Category category);
    List<Category> mapCategoryResponseListToCategoryList(List<CategoryResponse> categoryResponseList);
    List<CategoryResponse> mapCategoryListToCategoryResponseList(List<Category> categoryList);
    CategoryResponse mapCategoryRequestToCategoryResponse(CategoryRequest categoryRequest);
    Category mapCategoryRequestToCategory(CategoryRequest categoryRequest);
    @Mapping(target = "totalProducts", expression = "java(category.getProducts() != null ? category.getProducts().size() : 0)")
    CategoryReportDTO mapCategoryToCategoryReportDto(Category category);

    @Mapping(target = "totalProducts", expression = "java(categoryList.getProducts() != null ? categoryList.getProducts().size() : 0)")
    List<CategoryReportDTO> categoryListTocategoryReportDtoList(List<Category> categoryList);

//    public static CategoryResponse fromEntity(Category category) {
//        if (category == null) return null;
//        return CategoryResponse.builder()
//                .id(category.getId())
//                .name(category.getName())
//                .description(category.getDescription())
//                .build();
//    }

//    public static Category toEntity(CategoryRequest categoryRequest) {
//        if (categoryRequest == null) return null;
//        Category category = new Category();
//        category.setId(categoryRequest.id());
//        category.setName(categoryRequest.name());
//        category.setDescription(categoryRequest.description());
//        return category;
//    }
}
