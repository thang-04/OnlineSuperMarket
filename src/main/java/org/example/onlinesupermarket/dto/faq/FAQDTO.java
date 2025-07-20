package org.example.onlinesupermarket.dto.faq;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FAQDTO {

    private Integer faqId;

    @NotEmpty(message = "Câu hỏi không được để trống.")
    private String question;

    @NotEmpty(message = "Câu trả lời không được để trống.")
    private String answer;

    private int sortOrder;
    private boolean active = true;
    private String category = "General";
}