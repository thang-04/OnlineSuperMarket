package org.example.onlinesupermarket.mapper.product;

import org.example.onlinesupermarket.dto.product.ProductDTO;
import org.example.onlinesupermarket.dto.product.ProductDetailDTO;
import org.example.onlinesupermarket.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDetailDTO ProductDetailMapper(Product product) {
            ProductDetailDTO dto = new ProductDetailDTO();
            dto.setCategoryName(product.getCategory().getName());
            dto.setProductImages(product.getProductImages());
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());
            dto.setPrice(product.getPrice());
            dto.setStockQuantity(product.getStockQuantity());
            return dto;
    }
    public ProductDTO MapperMoreProduct(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(product.getProductId());
        dto.setProductImages(product.getProductImages());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        return dto;
    }
}
