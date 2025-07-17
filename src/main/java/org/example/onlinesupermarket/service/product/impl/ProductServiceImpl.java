package org.example.onlinesupermarket.service.product.impl;

import org.example.onlinesupermarket.dto.product.ProductDTO;
import org.example.onlinesupermarket.dto.product.ProductDetailDTO;
import org.example.onlinesupermarket.entity.Product;
import org.example.onlinesupermarket.mapper.product.ProductMapper;
import org.example.onlinesupermarket.repository.ProductRepository;
import org.example.onlinesupermarket.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;
//logic find all
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
//logic fill Cate
    @Override
    public List<Product> findByCategoryId(Integer categoryId) {
        return productRepository.findProductByCategoryCategoryId(categoryId);
    }
    //service detail
    @Override
    public ProductDetailDTO getProductDetail(Integer productId) {
        Optional<Product> product = productRepository.findWithImages(productId);
        if(product.isPresent()) {
            return productMapper.ProductDetailMapper(product.get());
        }
        else return null;
    }
//logic san pham tuong tu(fill theo category)
    @Override
    public List<ProductDTO> MoreLikeThis(Integer categoryId, Integer productId) {
        List<Product> products = productRepository.moreProduct(categoryId, productId);
        List<ProductDTO> dtoList = products.stream()
                .map(productMapper::MapperMoreProduct)
                .collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public List<ProductDTO> getTopBestSellingProducts(int topN) {
        Pageable pageable = PageRequest.of(0, topN);
        List<Product> products = productRepository.findTopBestSellingProducts(pageable);
        return products.stream().map(productMapper::MapperMoreProduct).collect(Collectors.toList());
    }

    @Override
    public List<Product> searchByName(String name, String sortByPrice) {
        if ("desc".equalsIgnoreCase(sortByPrice)) {
            return productRepository.findByNameContainingIgnoreCaseOrderByPriceDescNameAsc(name);
        } else {
            return productRepository.findByNameContainingIgnoreCaseOrderByPriceAscNameAsc(name);
        }
    }

    @Override
    public List<Product> filterByPrice(Double minPrice, Double maxPrice, String sortByPrice) {
        if ("desc".equalsIgnoreCase(sortByPrice)) {
            return productRepository.findByPriceBetweenOrderByPriceDescNameAsc(minPrice, maxPrice);
        } else {
            return productRepository.findByPriceBetweenOrderByPriceAscNameAsc(minPrice, maxPrice);
        }
    }

    @Override
    public List<Product> searchByNameAndPrice(String name, Double minPrice, Double maxPrice, String sortByPrice) {
        if ("desc".equalsIgnoreCase(sortByPrice)) {
            return productRepository.findByNameContainingIgnoreCaseAndPriceBetweenOrderByPriceDescNameAsc(name, minPrice, maxPrice);
        } else {
            return productRepository.findByNameContainingIgnoreCaseAndPriceBetweenOrderByPriceAscNameAsc(name, minPrice, maxPrice);
        }
    }

    @Override
    public List<Product> getAllProducts(String sortByPrice) {
        if ("desc".equalsIgnoreCase(sortByPrice)) {
            return productRepository.findAllByOrderByPriceDescNameAsc();
        } else {
            return productRepository.findAllByOrderByPriceAscNameAsc();
        }
    }
}
