package org.example.onlinesupermarket.service.blog.impl;

import org.example.onlinesupermarket.dto.blog.BlogDTO;
import org.example.onlinesupermarket.entity.Blog;
import org.example.onlinesupermarket.mapper.blog.BlogMapper;
import org.example.onlinesupermarket.repository.BlogRepository;
import org.example.onlinesupermarket.service.blog.BlogService;
import org.example.onlinesupermarket.service.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private FileService fileService;

    @Override
    public Page<BlogDTO> getBlogs(String title, Boolean published, Pageable pageable) {
        Page<Blog> blogPage = blogRepository.findWithFilters(title, published, pageable);
        return blogPage.map(blogMapper::toDto);
    }

    @Override
    public BlogDTO findById(Integer id) {
        return blogRepository.findById(id)
                .map(blogMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài viết với ID: " + id));
    }

    @Override
    public BlogDTO save(BlogDTO blogDTO, MultipartFile imageFile) {
        Blog blog;
        if (blogDTO.getBlogId() != null) {
            blog = blogRepository.findById(blogDTO.getBlogId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy bài viết với ID: " + blogDTO.getBlogId()));
        } else {
            blog = new Blog();
            blog.setCreatedAt(LocalDateTime.now());
        }

        blogMapper.updateEntityFromDto(blogDTO, blog);

        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = fileService.storeFile(imageFile);
            blog.setFeaturedImage("/uploads/" + fileName);
        }

        Blog savedBlog = blogRepository.save(blog);
        return blogMapper.toDto(savedBlog);
    }

    @Override
    public void deleteById(Integer id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài viết để xóa."));

        // Logic nghiệp vụ: Không cho xóa bài viết đã xuất bản
        if (blog.isPublished()) {
            throw new IllegalStateException("Không thể xóa bài viết đã được xuất bản.");
        }

        blogRepository.deleteById(id);
    }
}