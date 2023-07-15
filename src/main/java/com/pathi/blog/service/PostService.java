package com.pathi.blog.service;

import com.pathi.blog.payload.PostDto;
import com.pathi.blog.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto,long id);

    void deletePost(long id);

    List<PostDto> getPostsByCategoryId(Long categoryId);
}
