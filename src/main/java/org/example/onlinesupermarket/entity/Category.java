package org.example.onlinesupermarket.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    private String name;
    @Column(name="CategoryPreview")
    private String categoryPreview;
    @Column(length = 500)
    private String description;
}