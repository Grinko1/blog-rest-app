package com.blog.app.post.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostDto {
    private Integer id;
    private String title;
    private String content;
    private String image;
    private Date createdAt;
    private Integer categoryId;
}
