package org.example.onlinesupermarket.service.banner.impl;

import org.example.onlinesupermarket.dto.banner.BannerDTO;
import org.example.onlinesupermarket.entity.Banner;
import org.example.onlinesupermarket.mapper.banner.BannerMapper;
import org.example.onlinesupermarket.repository.BannerRepository;
import org.example.onlinesupermarket.service.banner.BannerService;
import org.example.onlinesupermarket.service.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private BannerMapper bannerMapper;

    @Autowired
    private FileService fileService;

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
    @Transactional
    public void createBanner(BannerDTO bannerDto, MultipartFile imageFile) {
        Banner banner = bannerMapper.toEntity(bannerDto);

        String imageUrl = fileService.storeFile(imageFile);
        banner.setImageUrl(imageUrl);

        banner.setCreatedAt(LocalDateTime.now());
        bannerRepository.save(banner);
    }

    @Override
    @Transactional
    public void updateBanner(Integer id, BannerDTO bannerDto, MultipartFile imageFile) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner not found"));

        bannerMapper.updateEntityFromDto(bannerDto, banner);

        if (imageFile != null && !imageFile.isEmpty()) {
            String newImageUrl = fileService.storeFile(imageFile);
            banner.setImageUrl(newImageUrl);
        }

        banner.setUpdatedAt(LocalDateTime.now());
        bannerRepository.save(banner);
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

    @Override
    @Transactional(readOnly = true)
    public Page<BannerDTO> getBanners(String title, Boolean active, Pageable pageable) {
        Page<Banner> bannerPage = bannerRepository.findWithFilters(title, active, pageable);
        return bannerPage.map(bannerMapper::toDto); // Giả sử bạn có mapper
    }
}