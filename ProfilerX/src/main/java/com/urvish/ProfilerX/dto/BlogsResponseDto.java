package com.urvish.ProfilerX.dto;

import lombok.Data;

import java.util.List;

@Data
public class BlogsResponseDto {

    private Long blogsId;

    private List<BlogDTO> allBlogs;

}
