package org.example.onlinesupermarket.mapper.banner;

import org.example.onlinesupermarket.dto.banner.BannerDTO;
import org.example.onlinesupermarket.entity.Banner;
import org.springframework.stereotype.Component;

@Component
public class BannerMapper {

    public BannerDTO toDto(Banner banner) {
        if (banner == null) {
            return null;
        }

        BannerDTO dto = new BannerDTO();
        dto.setBannerId(banner.getBannerId());
        dto.setTitle(banner.getTitle());
        dto.setDescription(banner.getDescription());
        dto.setImageUrl(banner.getImageUrl());
        dto.setLinkUrl(banner.getLinkUrl());
        dto.setSortOrder(banner.getSortOrder());
        dto.setActive(banner.isActive());
        dto.setCreatedAt(banner.getCreatedAt());
        dto.setUpdatedAt(banner.getUpdatedAt());

        return dto;
    }

    public Banner toEntity(BannerDTO bannerDto) {
        if (bannerDto == null) {
            return null;
        }

        Banner entity = new Banner();
        entity.setBannerId(bannerDto.getBannerId());
        entity.setTitle(bannerDto.getTitle());
        entity.setDescription(bannerDto.getDescription());
        entity.setImageUrl(bannerDto.getImageUrl());
        entity.setLinkUrl(bannerDto.getLinkUrl());
        entity.setSortOrder(bannerDto.getSortOrder());
        entity.setActive(bannerDto.isActive());
        return entity;
    }

    public void updateEntityFromDto(BannerDTO dto, Banner entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setLinkUrl(dto.getLinkUrl());
        entity.setSortOrder(dto.getSortOrder());
        entity.setActive(dto.isActive());
    }
}