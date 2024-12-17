package com.ll.tem.domain.post.post.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
public class Post {
    private static long lastId = 0;
    @Builder.Default
    private Long id = ++lastId;
    private String title;
    private String content;
}
