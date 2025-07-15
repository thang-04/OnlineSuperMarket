package org.example.onlinesupermarket.dto.blog;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BlogDTO {
    private Integer blogId;
    private String title;
    private String content;
    private String featuredImage;
    private String authorName;
    private boolean published = false;
    private LocalDateTime createdAt;
}