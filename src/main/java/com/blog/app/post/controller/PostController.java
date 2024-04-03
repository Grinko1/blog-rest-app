package com.blog.app.post.controller;

import com.blog.app.post.dto.PostDto;
import com.blog.app.post.dto.PostsDto;
import com.blog.app.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    @GetMapping
    public ResponseEntity<PostsDto> getAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy
    ){
        return new ResponseEntity<>(postService.findAll(pageNumber, pageSize, sortBy), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getById(@PathVariable("id") Integer id){
        System.out.println("here");
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> update(@RequestBody PostDto postDto ,@PathVariable("id") Integer id){
        return new ResponseEntity<>(postService.updatePost(postDto, id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostDto> create(@RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") Integer id){
        postService.deletePost(id);
        return "Post with id: " + id + " was deleted";
    }


    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable("categoryId") Integer categoryId) {

        List<PostDto> allPostByCategory = this.postService.getAllPostByCategory(categoryId);

        return new ResponseEntity<>(allPostByCategory, HttpStatus.OK);
    }

    //search post by title
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords){
        List<PostDto> post = this.postService.searchPostByKeyword(keywords);
        return new ResponseEntity<>(post,HttpStatus.OK);
    }

    @GetMapping("/search-contains/{keywords}")
    public ResponseEntity<List<PostDto>> searchPostByTitleContainsInPost(@PathVariable("keywords") String keywords){
        List<PostDto> post = this.postService.searchPostsTitleWithKeywordContains(keywords);
        return new ResponseEntity<>(post,HttpStatus.OK);
    }

}
