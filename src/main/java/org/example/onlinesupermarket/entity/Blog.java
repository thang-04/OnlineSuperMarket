package org.example.onlinesupermarket.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "Blogs")
@Data
@NoArgsConstructor
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer blogId;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(columnDefinition = "NVARCHAR(MAX)", nullable = false)
    private String content;

    @Column(length = 512)
    private String featuredImage;

    @Column(length = 255)
    private String authorName;

    @Column(name = "is_published", nullable = false)
    private boolean published = false;

    private LocalDateTime createdAt;

}