package com.blog.app.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PostsDto {
    private List<PostDto> posts;
    private int pageNumber;
    private int pageSize;
    private long totalElemets;
    private int totalPages;
    private boolean isLastPage;
}
