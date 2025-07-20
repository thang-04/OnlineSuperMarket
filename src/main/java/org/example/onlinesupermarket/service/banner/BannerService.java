package org.example.onlinesupermarket.service.banner;

import jakarta.validation.Valid;
import org.example.onlinesupermarket.dto.banner.BannerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface BannerService {
    public Object getAllBanners();


    public BannerDTO getBannerById(Integer id);

    void createBanner(BannerDTO bannerDto, MultipartFile imageFile);

    void updateBanner(Integer id, BannerDTO bannerDto, MultipartFile imageFile);

    public void deleteBanner(Integer id);

    public void toggleBannerStatus(Integer id);

    Page<BannerDTO> getBanners(String title, Boolean active, Pageable pageable);

}
