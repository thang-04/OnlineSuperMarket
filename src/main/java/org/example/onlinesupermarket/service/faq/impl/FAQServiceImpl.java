package org.example.onlinesupermarket.service.faq.impl;

import org.example.onlinesupermarket.dto.faq.FAQDTO;
import org.example.onlinesupermarket.entity.FAQ;
import org.example.onlinesupermarket.mapper.faq.FAQMapper;
import org.example.onlinesupermarket.repository.FAQRepository;
import org.example.onlinesupermarket.service.faq.FAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FAQServiceImpl implements FAQService {

    @Autowired
    private FAQRepository faqRepository;

    @Autowired
    private FAQMapper faqMapper;

    @Override
    public List<FAQDTO> getAllFAQs() {
        return faqRepository.findAllOrderBySortOrder().stream()
                .map(faqMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public FAQDTO getFAQById(Integer id) {
        return faqRepository.findById(id)
                .map(faqMapper::toDto)
                .orElseThrow(() -> new RuntimeException("FAQ not found with id: " + id));
    }

    @Override
    public FAQDTO saveFAQ(FAQDTO faqDto) {
        FAQ faq = (faqDto.getFaqId() != null)
                ? faqRepository.findById(faqDto.getFaqId()).orElse(new FAQ())
                : new FAQ();
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
    public List<FAQDTO> getActiveFAQs() {
        return faqRepository.findActiveOrderBySortOrder().stream()
                .map(faqMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<String> getActiveCategories() {
        return faqRepository.findAllActiveCategories();
    }
}