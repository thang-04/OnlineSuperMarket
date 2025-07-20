package org.example.onlinesupermarket.service.faq;

import org.example.onlinesupermarket.dto.faq.FAQDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface FAQService {
    Page<FAQDTO> getFAQs(String question, Boolean active, Pageable pageable);
    FAQDTO getFAQById(Integer id);
    FAQDTO saveFAQ(FAQDTO faqDto);
    void deleteFAQ(Integer id);
    void toggleFAQStatus(Integer id);
    List<String> getActiveCategories();
}