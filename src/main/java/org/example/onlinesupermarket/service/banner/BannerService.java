package org.example.onlinesupermarket.service.banner;

import jakarta.validation.Valid;
import org.example.onlinesupermarket.dto.banner.BannerDTO;

public interface BannerService {
    public Object getAllBanners();


    public BannerDTO getBannerById(Integer id);

    BannerDTO createBanner(BannerDTO bannerDto);

    public BannerDTO updateBanner(Integer id, BannerDTO bannerDto);

    public void deleteBanner(Integer id);

    public void toggleBannerStatus(Integer id);
}
