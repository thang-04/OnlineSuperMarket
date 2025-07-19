package org.example.onlinesupermarket.service.product.impl;

import org.example.onlinesupermarket.dto.product.ProductDTO;
import org.example.onlinesupermarket.dto.product.ProductDetailDTO;
import org.example.onlinesupermarket.entity.Product;
import org.example.onlinesupermarket.mapper.product.ProductMapper;
import org.example.onlinesupermarket.repository.ProductRepository;
import org.example.onlinesupermarket.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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
    public Page<Product> getAllProducts(int pageNo) {
        //truyeenf vao so trang de no phan trang
        //default cho pageNo = 1
        Pageable pageable = PageRequest.of(pageNo-1, 8);
        return productRepository.findAll(pageable);
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
        try {
            if(product.isEmpty() || product.get().getProductId() == null) {
                throw new org.springframework.data.crossstore.ChangeSetPersister.NotFoundException();
            }
            return productMapper.ProductDetailMapper(product.get());
        } catch (org.springframework.data.crossstore.ChangeSetPersister.NotFoundException e) {
            return null;
        }
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
    public List<ProductDTO> getTop10BestSellingProducts() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Object[]> results = productRepository.findTop10BestSellingProducts(pageable);
        return results.stream()
                .map(obj -> productMapper.MapperProductWithTotalSold((Product) obj[0], (Long) obj[1]))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductDTO> getAllBestSellingProducts(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Object[]> page = productRepository.findAllBestSellingProducts(pageable);
        return page.map(obj -> productMapper.MapperProductWithTotalSold((Product) obj[0], (Long) obj[1]));
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

    @Override
    public Page<Product> searchProducts(String name, Integer categoryId, Double minPrice, Double maxPrice, String sortByPrice, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        // Xây dựng query động
        if (name != null && !name.isEmpty() && categoryId != null && categoryId > 0 && minPrice != null && maxPrice != null) {
            // Search theo tên, category, giá
            return productRepository.findByNameContainingIgnoreCaseAndCategoryCategoryIdAndPriceBetween(
                name, categoryId, minPrice, maxPrice, pageable
            );
        } else if (name != null && !name.isEmpty() && categoryId != null && categoryId > 0) {
            // Search theo tên và category
            return productRepository.findByNameContainingIgnoreCaseAndCategoryCategoryId(
                name, categoryId, pageable
            );
        } else if (name != null && !name.isEmpty() && minPrice != null && maxPrice != null) {
            // Search theo tên và giá
            return productRepository.findByNameContainingIgnoreCaseAndPriceBetween(
                name, minPrice, maxPrice, pageable
            );
        } else if (categoryId != null && categoryId > 0 && minPrice != null && maxPrice != null) {
            // Search theo category và giá
            return productRepository.findByCategoryCategoryIdAndPriceBetween(
                categoryId, minPrice, maxPrice, pageable
            );
        } else if (name != null && !name.isEmpty()) {
            // Search theo tên
            return productRepository.findByNameContainingIgnoreCase(
                name, pageable
            );
        } else if (categoryId != null && categoryId > 0) {
            // Search theo category
            return productRepository.findByCategoryCategoryId(
                categoryId, pageable
            );
        } else if (minPrice != null && maxPrice != null) {
            // Search theo giá
            return productRepository.findByPriceBetween(
                minPrice, maxPrice, pageable
            );
        } else {
            // Tất cả sản phẩm
            return productRepository.findAll(pageable);
        }
    }

    @Override
    public Product getProductById(Integer productId) {
        return productRepository.findById(productId).orElse(null);
    }
}
