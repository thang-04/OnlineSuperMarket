package org.example.onlinesupermarket.dto.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.onlinesupermarket.entity.Category;
import org.example.onlinesupermarket.entity.ProductImage;

import java.time.LocalDateTime;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDetailDTO {
    private String categoryName;
    private String name;
    private String description;
    private Double price;
    private int stockQuantity;
    private List<ProductImage> productImages;
}
