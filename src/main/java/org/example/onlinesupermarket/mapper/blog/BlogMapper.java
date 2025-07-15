package org.example.onlinesupermarket.mapper.blog;

import org.example.onlinesupermarket.dto.blog.BlogDTO;
import org.example.onlinesupermarket.entity.Blog;
import org.springframework.stereotype.Component;

@Component
public class BlogMapper {

    public BlogDTO toDto(Blog blog) {
        if (blog == null) return null;

        BlogDTO dto = new BlogDTO();
        dto.setBlogId(blog.getBlogId());
        dto.setTitle(blog.getTitle());
        dto.setContent(blog.getContent());
        dto.setFeaturedImage(blog.getFeaturedImage());
        dto.setAuthorName(blog.getAuthorName());
        dto.setPublished(blog.isPublished());
        dto.setCreatedAt(blog.getCreatedAt());
        return dto;
    }

    public void updateEntityFromDto(BlogDTO dto, Blog entity) {
        if (dto == null || entity == null) return;

        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setAuthorName(dto.getAuthorName());
        entity.setPublished(dto.isPublished());

    }
}