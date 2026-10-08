package com.urvish.ProfilerX.service;

import com.urvish.ProfilerX.dto.BlogDTO;
import com.urvish.ProfilerX.dto.BlogsResponseDto;
import com.urvish.ProfilerX.entity.Blogs;
import com.urvish.ProfilerX.entity.Developer;
import com.urvish.ProfilerX.repository.BlogsRepository;
import com.urvish.ProfilerX.repository.DeveloperRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogsService {

    private final BlogsRepository blogsRepository;
    private final DeveloperRepository developerRepository;
    private final DeveloperService developerService;

    public BlogsResponseDto getBlogs(Long developerId) {
        Blogs existBlog = blogsRepository.findByDeveloperId(developerId).orElseThrow(() ->
                new RuntimeException("Blogs not found"));

        BlogsResponseDto blogsResponseDto = new BlogsResponseDto();

        blogsResponseDto.setAllBlogs(existBlog.getAllBlogs());
        blogsResponseDto.setBlogsId(existBlog.getBlogsId());

        return blogsResponseDto;
    }

    public BlogsResponseDto createBlogs(Long developerId, @Valid List<BlogDTO> blogs) {

        Developer developer = developerRepository.findById(developerId).orElseThrow(() ->
                new RuntimeException("Developer not found"));

        if (blogsRepository.findByDeveloperId(developerId).isPresent()) {
            throw new RuntimeException(
                    "Blogs already exist for this developer");
        }

        Blogs blogsEntity  = new Blogs();

        blogsEntity.setDeveloper(developer);
        blogsEntity.setAllBlogs(blogs);

        blogsEntity = blogsRepository.save(blogsEntity);

        developerService.update_timeStamp_updation(developerId);

        BlogsResponseDto blogsResponseDto = new BlogsResponseDto();

        blogsResponseDto.setAllBlogs(blogsEntity.getAllBlogs());
        blogsResponseDto.setBlogsId(blogsEntity.getBlogsId());

        return blogsResponseDto;
    }

    public BlogsResponseDto updateBlogs(Long developerId, @Valid List<BlogDTO> blogs) {

        Blogs existBlog = blogsRepository.findByDeveloperId(developerId).orElseThrow(() ->
                new RuntimeException("Blogs not found"));

        existBlog.setAllBlogs(blogs);

        existBlog = blogsRepository.save(existBlog);

        developerService.update_timeStamp_updation(developerId);

        BlogsResponseDto blogsResponseDto = new BlogsResponseDto();

        blogsResponseDto.setAllBlogs(existBlog.getAllBlogs());
        blogsResponseDto.setBlogsId(existBlog.getBlogsId());

        return blogsResponseDto;
    }

    public void deleteBlogs(Long developerId) {
        Blogs existing = blogsRepository.findByDeveloperId(developerId).orElseThrow(() ->
                new RuntimeException("Blogs not found"));

        blogsRepository.delete(existing);
    }
}
