package com.blog.app.comment.service;

import com.blog.app.comment.dto.CommentDto;
import com.blog.app.comment.entity.Comment;
import com.blog.app.comment.repository.CommentRepository;
import com.blog.app.exceptions.NotFoundException;
import com.blog.app.post.entity.Post;
import com.blog.app.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Comment comment =modelMapper.map(commentDto, Comment.class);
        Post post = postRepository.findById(postId).orElseThrow(()-> new NotFoundException("Post:" , "post id" , postId));
        comment.setPost(post);

        return modelMapper.map(commentRepository.save(comment), CommentDto.class);
    }

    @Override
    public List<CommentDto> findAllByPostId(Integer postId) {
        return commentRepository.findAllByPostId(postId).stream().map((comment) -> modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Integer commentId) {
        commentRepository.deleteById(commentId);
    }
}
