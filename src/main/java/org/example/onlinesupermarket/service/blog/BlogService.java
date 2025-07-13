package org.example.onlinesupermarket.service;

import org.example.onlinesupermarket.dto.blog.BlogDTO;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface BlogService {
    List<BlogDTO> getAllBlogs();
    BlogDTO findById(Integer id);
    BlogDTO save(BlogDTO blogDTO, MultipartFile imageFile);
    void deleteById(Integer id);
}