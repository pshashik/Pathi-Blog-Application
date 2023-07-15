package com.pathi.blog.service.impl;

import com.pathi.blog.entity.Category;
import com.pathi.blog.entity.Post;
import com.pathi.blog.exception.ResourceNotFoundException;
import com.pathi.blog.payload.PostDto;
import com.pathi.blog.payload.PostResponse;
import com.pathi.blog.repository.CategoryRepository;
import com.pathi.blog.repository.PostRepository;
import com.pathi.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper modelMapper;

    private CategoryRepository categoryRepository;

    public PostServiceImpl(PostRepository postRepository,
                           ModelMapper modelMapper,
                           CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category","Id", postDto.getCategoryId()));
        // Saving the client data to the database
        Post newPost = postRepository.save(mapToEntity(postDto));
        newPost.setCategory(category);
        // returning the post dto data by converting from entity
        return mapToDto(newPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.toString())
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);
        List<PostDto> listOfPosts =  posts.getContent().stream()
                .map(post -> mapToDto(post))
                .collect(Collectors.toList());
        return new PostResponse(posts.getNumber(),
                posts.getSize(),
                posts.getTotalPages(),
                posts.getTotalElements(), posts.isLast(),listOfPosts);
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto,long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category","Id", postDto.getCategoryId()));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post newPost = postRepository.save(post);
        newPost.setCategory(category);
        return mapToDto(newPost);
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPostsByCategoryId(Long categoryId) {
       Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","Id",categoryId));
       List<Post> posts = postRepository.findByCategoryId(categoryId);
        return posts.stream()
                .map(post -> mapToDto(post))
                .collect(Collectors.toList());
    }


    private PostDto mapToDto(Post post){
        return modelMapper.map(post,PostDto.class);
    }
    private Post mapToEntity(PostDto postDto){
        return modelMapper.map(postDto,Post.class);
    }
}
