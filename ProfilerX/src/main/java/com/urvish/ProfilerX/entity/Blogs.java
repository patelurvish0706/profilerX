package com.urvish.ProfilerX.entity;

import com.urvish.ProfilerX.dto.BlogDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

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

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<BlogDTO> allBlogs = new ArrayList<>();

}
