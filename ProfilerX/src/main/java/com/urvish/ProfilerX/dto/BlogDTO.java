package com.urvish.ProfilerX.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BlogDTO {

    private String blogTitle;
    private String blogDescription;

    private LocalDate createdAt;

}
