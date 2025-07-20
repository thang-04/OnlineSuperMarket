package org.example.onlinesupermarket.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String name;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String description;

    private Double price;
    private String image;
    private int stockQuantity;
    private boolean status = false;

    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WishList> wishLists;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductImage> productImages;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;
}