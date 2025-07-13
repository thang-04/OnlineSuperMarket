package org.example.onlinesupermarket.service.blog.impl;

import org.example.onlinesupermarket.dto.blog.BlogDTO;
import org.example.onlinesupermarket.entity.Blog;
import org.example.onlinesupermarket.mapper.blog.BlogMapper;
import org.example.onlinesupermarket.repository.BlogRepository;
import org.example.onlinesupermarket.service.BlogService;
import org.example.onlinesupermarket.service.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private FileService fileStorageService;

    @Override
    public List<BlogDTO> getAllBlogs() {
        return blogRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(blogMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BlogDTO findById(Integer id) {
        return blogRepository.findById(id)
                .map(blogMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài viết với ID: " + id));
    }

    @Override
    public BlogDTO save(BlogDTO blogDTO, MultipartFile imageFile) {
        Blog blog = (blogDTO.getBlogId() != null)
                ? blogRepository.findById(blogDTO.getBlogId()).orElse(new Blog())
                : new Blog();

        blogMapper.updateEntityFromDto(blogDTO, blog);

        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = fileStorageService.storeFile(imageFile);
            blog.setFeaturedImage("/uploads/" + fileName);
        }

        Blog savedBlog = blogRepository.save(blog);
        return blogMapper.toDto(savedBlog);
    }

    @Override
    public void deleteById(Integer id) {
        if (!blogRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy bài viết để xóa.");
        }
        blogRepository.deleteById(id);
    }
}