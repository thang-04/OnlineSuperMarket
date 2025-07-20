package org.example.onlinesupermarket.dto.blog;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BlogDTO {
    private Integer blogId;

    @NotEmpty(message = "Tiêu đề không được để trống.")
    @Size(max = 255, message = "Tiêu đề không được vượt quá 255 ký tự.")
    private String title;

    @NotEmpty(message = "Nội dung không được để trống.")
    private String content;

    private String featuredImage;
    private String authorName;
    private boolean published = false;
    private LocalDateTime createdAt;
}