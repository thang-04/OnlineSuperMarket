package org.example.onlinesupermarket.service.banner.impl;

import org.example.onlinesupermarket.dto.banner.BannerDTO;
import org.example.onlinesupermarket.entity.Banner;
import org.example.onlinesupermarket.mapper.banner.BannerMapper;
import org.example.onlinesupermarket.repository.BannerRepository;
import org.example.onlinesupermarket.service.banner.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private BannerMapper bannerMapper;

    public List<BannerDTO> getAllBanners() {
        return bannerRepository.findAllOrderBySortOrder().stream()
                .map(bannerMapper::toDto)
                .collect(Collectors.toList());
    }

    public BannerDTO getBannerById(Integer id) {
        return bannerRepository.findById(id)
                .map(bannerMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Banner not found with id: " + id));
    }

    @Override
    public BannerDTO createBanner(BannerDTO bannerDto) {
        Banner banner = bannerMapper.toEntity(bannerDto);
        Banner savedBanner = bannerRepository.save(banner);
        return bannerMapper.toDto(savedBanner);
    }

    @Override
    public BannerDTO updateBanner(Integer id, BannerDTO bannerDto) {
        Banner existingBanner = bannerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner not found with id: " + id));

        bannerMapper.updateEntityFromDto(bannerDto, existingBanner);

        Banner updatedBanner = bannerRepository.save(existingBanner);
        return bannerMapper.toDto(updatedBanner);
    }

    @Override
    public void deleteBanner(Integer id) {
        if (!bannerRepository.existsById(id)) {
            throw new RuntimeException("Banner not found with id: " + id);
        }
        bannerRepository.deleteById(id);
    }

    @Override
    public void toggleBannerStatus(Integer id) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner not found with id: " + id));
        banner.setActive(!banner.isActive());
        bannerRepository.save(banner);
    }
}