package com.pathi.blog.service;

import com.pathi.blog.payload.CommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    CommentDto createComment(Long postId,CommentDto commentDto);

    List<CommentDto> getAllComments(Long postId);

    CommentDto getCommentById(Long postId, Long commentId);

    CommentDto updateComment(Long postId, Long commentId,CommentDto commentDto);

    void deleteComment(long postId, long commentId);
}
