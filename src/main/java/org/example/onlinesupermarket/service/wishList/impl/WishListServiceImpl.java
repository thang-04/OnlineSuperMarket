package org.example.onlinesupermarket.service.wishList.impl;

import org.example.onlinesupermarket.entity.WishList;
import org.example.onlinesupermarket.entity.User;
import org.example.onlinesupermarket.entity.Product;
import org.example.onlinesupermarket.repository.WishListRepository;
import org.example.onlinesupermarket.service.wishList.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WishListServiceImpl implements WishListService {
    @Autowired
    private WishListRepository wishListRepository;

    @Override
    public List<WishList> getWishListByUser(User user) {
        return wishListRepository.findByUser(user);
    }

    @Override
    public WishList addProductToWishList(User user, Product product) {
        WishList existing = wishListRepository.findByUserAndProduct(user, product);
        if (existing == null) {
            WishList wishList = new WishList();
            wishList.setUser(user);
            wishList.setProduct(product);
            return wishListRepository.save(wishList);
        }
        return existing;
    }

    @Override
    public void removeProductFromWishList(User user, Product product) {
        WishList wishList = wishListRepository.findByUserAndProduct(user, product);
        if (wishList != null) {
            wishListRepository.delete(wishList);
        }
    }

    @Override
    public boolean isProductInWishList(User user, Product product) {
        return wishListRepository.findByUserAndProduct(user, product) != null;
    }
} 