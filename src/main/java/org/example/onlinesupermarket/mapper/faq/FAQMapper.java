package org.example.onlinesupermarket.mapper.faq;

import org.example.onlinesupermarket.dto.faq.FAQDTO;
import org.example.onlinesupermarket.entity.FAQ;
import org.springframework.stereotype.Component;

@Component
public class FAQMapper {

    public FAQDTO toDto(FAQ faq) {
        if (faq == null) return null;
        FAQDTO dto = new FAQDTO();
        dto.setFaqId(faq.getFaqId());
        dto.setQuestion(faq.getQuestion());
        dto.setAnswer(faq.getAnswer());
        dto.setSortOrder(faq.getSortOrder());
        dto.setActive(faq.isActive());
        dto.setCategory(faq.getCategory());
        return dto;
    }

    public FAQ toEntity(FAQDTO dto) {
        if (dto == null) return null;
        FAQ entity = new FAQ();
        entity.setFaqId(dto.getFaqId());
        entity.setQuestion(dto.getQuestion());
        entity.setAnswer(dto.getAnswer());
        entity.setSortOrder(dto.getSortOrder());
        entity.setActive(dto.isActive());
        entity.setCategory(dto.getCategory());
        return entity;
    }

    public void updateEntityFromDto(FAQDTO dto, FAQ entity) {
        if (dto == null || entity == null) return;
        entity.setQuestion(dto.getQuestion());
        entity.setAnswer(dto.getAnswer());
        entity.setSortOrder(dto.getSortOrder());
        entity.setActive(dto.isActive());
        entity.setCategory(dto.getCategory());
    }
}