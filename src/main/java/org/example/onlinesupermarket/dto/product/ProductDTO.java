package org.example.onlinesupermarket.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.onlinesupermarket.entity.ProductImage;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDTO {
    private Integer productId;
    private String name;
    private Double price;
    private String images;
    private Long totalSold; // Số lượng bán ra cho sản phẩm bán chạy
}
