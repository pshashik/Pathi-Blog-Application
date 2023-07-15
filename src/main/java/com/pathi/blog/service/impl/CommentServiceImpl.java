package com.pathi.blog.service.impl;

import com.pathi.blog.entity.Comment;
import com.pathi.blog.entity.Post;
import com.pathi.blog.exception.BlogAPIException;
import com.pathi.blog.exception.ResourceNotFoundException;
import com.pathi.blog.payload.CommentDto;
import com.pathi.blog.repository.CommentRepository;
import com.pathi.blog.repository.PostRepository;
import com.pathi.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private ModelMapper mapper;
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(Long postId,CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getAllComments(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        return mapToDto(validatePostAndCommentId(postId,commentId));
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId,CommentDto commentDto) {
        Comment comment = validatePostAndCommentId(postId,commentId);
        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(long postId, long commentId){
        commentRepository.delete(validatePostAndCommentId(postId,commentId));
    }


    private Comment validatePostAndCommentId(long postId,long commentId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comments","id",commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment does not belongs to post");
        }
        return comment;
    }
    private Comment mapToEntity(CommentDto commentDto){
        return mapper.map(commentDto,Comment.class);
    }
    private CommentDto mapToDto(Comment comment){
        return mapper.map(comment,CommentDto.class);
    }
}
