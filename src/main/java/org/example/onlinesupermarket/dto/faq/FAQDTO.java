package org.example.onlinesupermarket.dto.faq;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FAQDTO {

    private Integer faqId;

    @NotEmpty(message = "Câu hỏi không được để trống.")
    @Size(max = 255, message = "Câu hỏi không được vượt quá 255 ký tự.")
    private String question;

    @NotEmpty(message = "Câu trả lời không được để trống.")
    @Size(max = 2000, message = "Câu trả lời không được vượt quá 2000 ký tự.")
    private String answer;

    @Min(value = 0, message = "Thứ tự sắp xếp phải là số không âm.")
    private int sortOrder = 0;

    private boolean active = true;

    @NotEmpty(message = "Thể loại không được để trống.")
    @Size(max = 100, message = "Thể loại không được vượt quá 100 ký tự.")
    private String category = "General";
}