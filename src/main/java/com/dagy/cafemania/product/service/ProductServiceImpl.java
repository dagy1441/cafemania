package com.dagy.cafemania.product.service;

import com.dagy.cafemania.categories.Category;
import com.dagy.cafemania.categories.payload.CategoryMapper;
import com.dagy.cafemania.categories.payload.CategoryRequest;
import com.dagy.cafemania.categories.service.CategoryRepository;
import com.dagy.cafemania.product.model.Product;
import com.dagy.cafemania.product.payload.ProductMapper;
import com.dagy.cafemania.product.payload.ProductRequest;
import com.dagy.cafemania.product.payload.ProductResponse;
import com.dagy.cafemania.product.payload.ProductSearchRequest;
import com.dagy.cafemania.shared.exceptions.BusinessLogicException;
import com.dagy.cafemania.shared.exceptions.EntityAllReadyExistException;
import com.dagy.cafemania.shared.exceptions.EntityNotFoundException;
import com.dagy.cafemania.shared.exceptions.ResourceNotFoundException;
import com.dagy.cafemania.shared.utilities.pagination.PaginationUtils;
import com.dagy.cafemania.shared.utilities.pagination.SortItem;
import com.dagy.cafemania.shared.validators.ObjectsValidator;
import com.dagy.cafemania.user.User;
import com.dagy.cafemania.user.payload.UserMapper;
import com.dagy.cafemania.user.payload.UserResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ObjectsValidator<ProductRequest> validator;

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final CategoryMapper categoryMapper;


    @Override
    public ProductResponse save(String categoryId, ProductRequest request) {

        validator.validate(request);

        Product productFromDB = productRepository.findByName(request.name());

        if (productFromDB != null)  throw new EntityAllReadyExistException("Product", "name", request.name());

        Category categoryFromDB = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category", "id", categoryId));;

        Product product = productMapper.mapProductRequestToProduct(request);

        product.setCategory(categoryFromDB);

        Product savedProduct = productRepository.save(product);

        return productMapper.mapProductToProductResponse(savedProduct);
    }

    @Override
    public ProductResponse update(String productId, String categoryId, ProductRequest request) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category", "id", categoryId));


        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product", "id", categoryId));

        if ( !productBelongsToCategory(category, product)) {
            throw new BusinessLogicException("Ce produit n'appartient à aucune categorie");
        }

        // just to be safe that the object does not have another id
        ProductRequest.builder().id(productId).build();

        Product productToBeSaved = productMapper.mapProductRequestToProduct(request);

        // assign the product to the current category
        productToBeSaved.setCategory(category);

        Product savedProduct = productRepository.save(productToBeSaved);

        return productMapper.mapProductToProductResponse(savedProduct);
    }

    @Override
    public ProductResponse findProductById(String categoryId,String productId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category", "id", categoryId));


        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product", "id", categoryId));

        if ( !productBelongsToCategory(category, product)) {
            throw new BusinessLogicException("Ce produit n'appartient à aucune categorie");
        }

        return productMapper.mapProductToProductResponse(product);
    }

    @Override
    public List<ProductResponse> findAll() {
        return null;
    }

    @Override
    public List<ProductResponse> getProductsByCategoryId(String categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category", "id", categoryId));

        List<Product> products = productRepository.findByCategoryId(category.getId());

        return productMapper.mapProductListToProductResponseList(products);
    }

    @Override
    public Page<ProductResponse> getAllProductsUsingPagination(ProductSearchRequest productSearchRequest) {

        Integer page = productSearchRequest.getPage();
        Integer size = productSearchRequest.getSize();
        List<SortItem> sortList = productSearchRequest.getSortList();

        // this pageable will be used for the pagination.
        Pageable pageable = PaginationUtils.createPageableBasedOnPageAndSizeAndSorting(sortList, page, size);

        Page<Product> recordsFromDb = productRepository.getAllProductsUsingPagination(productSearchRequest, pageable);

        List<ProductResponse> result = productMapper.mapProductListToProductResponseList(recordsFromDb.getContent());

        return new PageImpl<>(result, pageable, result.size());
    }

    @Override
    public Page<ProductResponse> findWithPaginationAndSorting(int page, int size, String field) {
        log.info("Récupération de tous les produits par pagination et par ordre decroissant");
        Pageable pageable;
        if ("name".equalsIgnoreCase(field)) {
            pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        } else if ("price".equalsIgnoreCase(field)) {
            pageable = PageRequest.of(page, size, Sort.by("price").ascending());
        } else {
            throw new IllegalArgumentException("Invalid field for sorting: " + field);
        }

        Page<Product> productPage = productRepository.findAll(pageable);

        List<ProductResponse> productResponses = productMapper.mapProductListToProductResponseList(productPage.getContent());

//        return new PageImpl<>(userResponses, pageable, userPage.getTotalElements());
        return new PageImpl<>(productResponses);
    }

    @Override
    public void delete(String categoryId, String productId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category", "id", categoryId));


        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product", "id", categoryId));

        if ( !productBelongsToCategory(category, product)) {
            throw new BusinessLogicException("Ce produit n'appartient à aucune categorie");
        }

         productMapper.mapProductToProductResponse(product);
    }

    private boolean productBelongsToCategory(Category categoryFromDB, Product productFromDB) {

        if (categoryFromDB == null || productFromDB.getCategory() == null) {
            return false;
        }

        String categoryId = productFromDB.getCategory().getId();
        return categoryId != null && categoryId.equals(categoryFromDB.getId());
    }

}
