package com.blog.app.comment.service;

import com.blog.app.comment.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Integer postId);
    List<CommentDto> findAllByPostId(Integer postId);

    void deleteComment(Integer commentId);
}
