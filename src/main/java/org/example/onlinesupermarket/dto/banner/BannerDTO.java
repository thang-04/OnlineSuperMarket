package org.example.onlinesupermarket.dto.banner;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BannerDTO {
    private Integer bannerId;

    @NotEmpty(message = "Title is required")
    private String title;

    private String description;

    @NotEmpty(message = "Image URL is required")
    private String imageUrl;

    private String linkUrl;
    private int sortOrder = 0;
    private boolean active = true;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}