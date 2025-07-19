package org.example.onlinesupermarket.service.wishList;

import org.example.onlinesupermarket.entity.WishList;
import org.example.onlinesupermarket.entity.User;
import org.example.onlinesupermarket.entity.Product;
import java.util.List;

public interface WishListService {
    List<WishList> getWishListByUser(User user);
    WishList addProductToWishList(User user, Product product);
    void removeProductFromWishList(User user, Product product);
    boolean isProductInWishList(User user, Product product);
} 