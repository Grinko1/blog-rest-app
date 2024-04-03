package com.blog.app.post.service;

import com.blog.app.category.entity.Category;
import com.blog.app.category.repository.CategoryRepository;
import com.blog.app.exceptions.NotFoundException;
import com.blog.app.post.dto.PostDto;
import com.blog.app.post.dto.PostsDto;
import com.blog.app.post.entity.Post;
import com.blog.app.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public PostsDto findAll(Integer pageNumber, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());

        Page<Post> pageOfPost = this.postRepository.findAll(pageable);
        List<PostDto> posts = postRepository.findAll().stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        PostsDto postResponse = new PostsDto();

        postResponse.setPosts(posts);
        postResponse.setPageNumber(pageOfPost.getNumber());
        postResponse.setPageSize(pageOfPost.getSize());
        postResponse.setTotalElemets(pageOfPost.getTotalElements());
        postResponse.setTotalPages(pageOfPost.getTotalPages());
        postResponse.setLastPage(pageOfPost.isLast());
        return postResponse;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Category category = categoryRepository.findById(postDto.getCategoryId()).orElse(null);
        Post post = modelMapper.map(postDto, Post.class);

        post.setCategory(category);
        post.setCreatedAt(new Date());

        return modelMapper.map(postRepository.save(post), PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElse(null);
        Post post = getPostEntityById(postId);

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setCategory(category);
        post.setImage(postDto.getImage());
        Post updatedPost = this.postRepository.save(post);
        return this.modelMapper.map(updatedPost, PostDto.class);

    }

    @Override
    public void deletePost(Integer postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = getPostEntityById(postId);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getAllPostByCategory(Integer categoryId) {
        Category cat = this.categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category", "categoryId", categoryId));
        return this.postRepository.findByCategory(cat).stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPostByKeyword(String keyword) {
        System.out.println(keyword + " this is keyword...");
        //List<Post> posts = this.postRepository.findByPostTitleContaining(keyword);
        List<Post> posts = this.postRepository.findByTitle(keyword);
        System.out.println(posts.toString());
        List<PostDto> postDto = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        System.out.println("Here ");
        return postDto;
    }

    @Override
    public List<PostDto> searchPostsTitleWithKeywordContains(String keyword) {
        List<Post> posts = this.postRepository.findByTitleContaining(keyword);

        return posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }

    private Post getPostEntityById(Integer postId) {
        return this.postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post", "postId", postId));
    }
}
