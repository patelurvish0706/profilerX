package com.urvish.ProfilerX.controller;

import com.urvish.ProfilerX.dto.BlogDTO;
import com.urvish.ProfilerX.dto.BlogsResponseDto;
import com.urvish.ProfilerX.service.BlogsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile/blogs")
@RequiredArgsConstructor
public class BlogsController {

    private final BlogsService blogsService;

    @GetMapping("/{developerId}")
    public BlogsResponseDto getBlogs(@PathVariable Long developerId) {

        return blogsService.getBlogs(developerId);

    }

    @PostMapping("/{developerId}")
    public BlogsResponseDto createBlogs(@PathVariable Long developerId, @Valid @RequestBody List<BlogDTO> blogs) {

        return blogsService.createBlogs(developerId, blogs);

    }

    @PutMapping("/{developerId}")
    public BlogsResponseDto updateBlogs(@PathVariable Long developerId, @Valid @RequestBody List<BlogDTO> blogs) {

        return blogsService.updateBlogs(developerId, blogs);

    }

    @DeleteMapping("/{developerId}")
    public ResponseEntity<String> deleteBlogs(@PathVariable Long developerId) {

        blogsService.deleteBlogs(developerId);

        return new ResponseEntity<>("Blogs Deleted Successfully", HttpStatus.NO_CONTENT);

    }
}
