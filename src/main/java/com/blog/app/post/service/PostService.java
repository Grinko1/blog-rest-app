package com.blog.app.post.service;

import com.blog.app.post.dto.PostDto;
import com.blog.app.post.dto.PostsDto;

import java.util.List;

public interface PostService {
    PostsDto findAll(Integer pageNumber, Integer pageSize, String sortBy);
    PostDto createPost(PostDto postDto);
    PostDto updatePost(PostDto postDto, Integer postId);
    void deletePost(Integer postId);
    PostDto getPostById(Integer postId);


    List<PostDto> getAllPostByCategory(Integer categoryId);
    List<PostDto> searchPostByKeyword(String keyword);
    List<PostDto> searchPostsTitleWithKeywordContains(String keyword);
}
