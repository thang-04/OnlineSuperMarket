package org.example.onlinesupermarket.service.faq;

import org.example.onlinesupermarket.dto.faq.FAQDTO;
import java.util.List;

public interface FAQService {
    List<FAQDTO> getAllFAQs();
    FAQDTO getFAQById(Integer id);
    FAQDTO saveFAQ(FAQDTO faqDto);
    void deleteFAQ(Integer id);
    void toggleFAQStatus(Integer id);

    List<FAQDTO> getActiveFAQs();
    List<String> getActiveCategories();
}