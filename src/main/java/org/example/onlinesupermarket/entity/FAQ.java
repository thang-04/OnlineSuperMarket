package org.example.onlinesupermarket.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FAQs")
public class FAQ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer faqId;

    @Column(nullable = false)
    private String question;

    @Column(columnDefinition = "NVARCHAR(MAX)", nullable = false)
    private String answer;

    @Column(nullable = false)
    private int sortOrder = 0;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private String category = "General";

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

}