package org.example.onlinesupermarket.service.blog;

import org.example.onlinesupermarket.dto.blog.BlogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface BlogService {
    Page<BlogDTO> getBlogs(String title, Boolean published, Pageable pageable);
    BlogDTO findById(Integer id);
    BlogDTO save(BlogDTO blogDTO, MultipartFile imageFile);
    void deleteById(Integer id);
}