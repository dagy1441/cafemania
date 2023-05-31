package com.dagy.cafemania.product.payload;

import com.dagy.cafemania.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product mapProductResponseToProduct(ProductResponse productResponse);
    ProductResponse mapProductToProductResponse(Product product);
    ProductResponse mapProductRequestToProductResponse(ProductRequest productRequest);
    Product mapProductRequestToProduct(ProductRequest productRequest);

    List<Product> mapProductResponseListToProductList(List<ProductResponse> productResponseList);
    List<ProductResponse> mapProductListToProductResponseList(List<Product> productList);
    List<ProductResponse> mapProductRequestListToProductResponseList(List<ProductRequest> productRequestList);

    @Mapping(target = "categoryName", expression = "java(product.getCategory() != null ? product.getCategory().getName() : null)")
    ProductReportDTO mapProductToProductReportDto(Product product);

    @Mapping(target = "categoryName", expression = "java(productList.getCategory() != null ? productList.getCategory().getName() : null)")
    List<ProductReportDTO> mapProductListToProductReportDto(List<Product> productList);
//    public static ProductResponse fromEntity(Product product) {
//        if (product == null) return null;
//        return ProductResponse.builder()
//                .id(product.getId())
//                .name(product.getName())
//                .description(product.getDescription())
//                .price(product.getPrice())
//                .build();
//    }

    public static Product toEntity(ProductRequest productRequest) {
        if (productRequest == null) return null;
        Product product = new Product();
        product.setId(productRequest.id());
        product.setName(productRequest.name());
        product.setDescription(productRequest.description());
        product.setPrice(productRequest.price());
        return product;
    }
}
