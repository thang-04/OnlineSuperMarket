package org.example.onlinesupermarket.dto.banner;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
public class BannerDTO {
    private Integer bannerId;

    @NotEmpty(message = "Tiêu đề không được để trống")
    @Size(max = 255, message = "Tiêu đề không được vượt quá 255 ký tự")
    private String title;

    @Size(max = 1000, message = "Mô tả không được vượt quá 1000 ký tự")
    private String description;

    // Xóa @NotEmpty và @URL vì trường này sẽ được tạo tự động sau khi upload file
    private String imageUrl;

    @URL(message = "URL liên kết phải là một địa chỉ web hợp lệ")
    private String linkUrl;

    @Min(value = 0, message = "Thứ tự sắp xếp phải là một số không âm")
    private int sortOrder = 0;

    private boolean active = true;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}