package com.blog.app.post.repository;

import com.blog.app.category.entity.Category;
import com.blog.app.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByCategory(Category category);

    List<Post> findByTitle(String keyword);

    List<Post> findByTitleContaining(String keyword);
}
