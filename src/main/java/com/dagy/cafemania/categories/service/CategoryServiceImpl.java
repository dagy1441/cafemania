package com.dagy.cafemania.categories.service;

import com.dagy.cafemania.categories.Category;
import com.dagy.cafemania.categories.payload.CategoryMapper;
import com.dagy.cafemania.categories.payload.CategoryRequest;
import com.dagy.cafemania.categories.payload.CategoryResponse;
import com.dagy.cafemania.categories.payload.CategorySearchRequest;
import com.dagy.cafemania.shared.exceptions.EntityAllReadyExistException;
import com.dagy.cafemania.shared.exceptions.EntityNotFoundException;
import com.dagy.cafemania.shared.exceptions.ResourceNotFoundException;
import com.dagy.cafemania.shared.utilities.pagination.PaginationUtils;
import com.dagy.cafemania.shared.utilities.pagination.SortItem;
import com.dagy.cafemania.shared.validators.ObjectsValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ObjectsValidator<CategoryRequest> validator;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse save(CategoryRequest request) {
        validator.validate(request);
        Optional<Category> category = categoryRepository.findByName(request.name());
        if (category.isPresent()) {
            log.warn("Categorie with name {} allready exist DB ", request.name());
            throw new EntityAllReadyExistException(
                    " Categorie",
                    " name",
                    request.name());
        }


        return  categoryMapper
                .mapCategoryToCategoryResponse(
                        categoryRepository.save(categoryMapper.mapCategoryRequestToCategory(request))
                );
    }

    @Override
    public CategoryResponse update(String id, CategoryRequest request) {
        return null;
    }

    @Override
    public CategoryResponse findById(String id) {

        if (id == null) {
            log.error("Category ID est null");
            return null;
        }
        Optional<Category> category = categoryRepository.findById(id);

        return Optional.of(
                        categoryMapper.mapCategoryToCategoryResponse(category.get()))
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Categorie " ,"ID", id)
                );
    }

    @Override
    public CategoryResponse findByName(String name) {
        return null;
    }

    @Override
    public Page<CategoryResponse> getAllCategoriesUsingPagination(CategorySearchRequest categorySearchRequest) {

        Integer page = categorySearchRequest.getPage();
        Integer size = categorySearchRequest.getSize();
        List<SortItem> sortList = categorySearchRequest.getSortList();
        // this pageable will be used for the pagination.
        Pageable pageable = PaginationUtils.createPageableBasedOnPageAndSizeAndSorting(sortList, page, size);

        Page<Category> recordsFromDb = categoryRepository.getAllCategoriesUsingPagination(categorySearchRequest, pageable);

        List<CategoryResponse> result = categoryMapper.mapCategoryListToCategoryResponseList(recordsFromDb.getContent());

        return new PageImpl<>(result, pageable, result.size());

    }

    @Override
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::mapCategoryToCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        if (id == null) {
            log.error("Category ID est null");
            return;
        }

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category {} not found"+ id));

        categoryRepository.delete(category);
    }
}
