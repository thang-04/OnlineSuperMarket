package org.example.onlinesupermarket.service.faq.impl;

import org.example.onlinesupermarket.dto.faq.FAQDTO;
import org.example.onlinesupermarket.entity.FAQ;
import org.example.onlinesupermarket.mapper.faq.FAQMapper;
import org.example.onlinesupermarket.repository.FAQRepository;
import org.example.onlinesupermarket.service.faq.FAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FAQServiceImpl implements FAQService {

    @Autowired
    private FAQRepository faqRepository;

    @Autowired
    private FAQMapper faqMapper;

    @Override
    public Page<FAQDTO> getFAQs(String question, Boolean active, Pageable pageable) {
        Page<FAQ> faqPage = faqRepository.findWithFiltersAndPagination(question, active, pageable);
        return faqPage.map(faqMapper::toDto);
    }

    @Override
    public FAQDTO getFAQById(Integer id) {
        return faqRepository.findById(id)
                .map(faqMapper::toDto)
                .orElseThrow(() -> new RuntimeException("FAQ not found with id: " + id));
    }

    @Override
    public FAQDTO saveFAQ(FAQDTO faqDto) {
        FAQ faq;
        if (faqDto.getFaqId() != null) {
            faq = faqRepository.findById(faqDto.getFaqId())
                    .orElseThrow(() -> new RuntimeException("FAQ not found with id: " + faqDto.getFaqId()));
        } else {
            faq = new FAQ();
        }
        faqMapper.updateEntityFromDto(faqDto, faq);
        FAQ savedFAQ = faqRepository.save(faq);
        return faqMapper.toDto(savedFAQ);
    }

    @Override
    public void deleteFAQ(Integer id) {
        if (!faqRepository.existsById(id)) {
            throw new RuntimeException("FAQ not found with id: " + id);
        }
        faqRepository.deleteById(id);
    }

    @Override
    public void toggleFAQStatus(Integer id) {
        FAQ faq = faqRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FAQ not found with id: " + id));
        faq.setActive(!faq.isActive());
        faqRepository.save(faq);
    }

    @Override
    public List<String> getActiveCategories() {
        return faqRepository.findAllActiveCategories();
    }
}