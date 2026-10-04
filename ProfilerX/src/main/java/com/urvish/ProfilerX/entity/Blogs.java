package com.urvish.ProfilerX.entity;

import com.urvish.ProfilerX.dto.BlogDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "Blogs")
public class Blogs {

    @ManyToOne
    @JoinColumn(name = "developer_id")
    private Developer developer;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blogsId;

    private ArrayList<BlogDTO> allBlogs;

}
