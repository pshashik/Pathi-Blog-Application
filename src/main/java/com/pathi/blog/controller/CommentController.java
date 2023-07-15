package com.pathi.blog.controller;

import com.pathi.blog.entity.Comment;
import com.pathi.blog.payload.CommentDto;
import com.pathi.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/")
@Tag(name="CRUD REST APIs For Comment Resource")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(
            summary = "Create Comment for a Post By PostId using REST API",
            description = "Create Comment for a Post By PostId REST API is used to create a comment to the database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @SecurityRequirement(
            name="Authentication"
    )
    @PostMapping("posts/{postId}/comments")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") Long postId, @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all Comment for a Post By PostId using REST API",
            description = "Get all Comment for a Post By PostId REST API is used to get all comments from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @GetMapping("posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getAllComments(@PathVariable(value = "postId") Long postId) {
        return ResponseEntity.ok(commentService.getAllComments(postId));
    }

    @Operation(
            summary = "Get a Comment By CommentId for a Post By PostId using REST API",
            description = "Get a Comment By CommentId for a Post By PostId REST API is used to get single comment from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @GetMapping("posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") Long postId,
                                                     @PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(commentService.getCommentById(postId, id));
    }

    @Operation(
            summary = "Update Comment By CommentId for a Post By PostId using REST API",
            description = "Update Comment By CommentId for a Post By PostId REST API is used to update single comment from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @SecurityRequirement(
            name = "Authentication"
    )
    @PutMapping("posts/{postId}/comments/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(name = "postId") long postId,
                                                    @PathVariable(name = "id") long id,
                                                    @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.updateComment(postId, id, commentDto), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Comment By CommentId for a Post By PostId using REST API",
            description = "Delete Comment By CommentId for a Post By PostId REST API is used to delete single comment from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    @SecurityRequirement(
            name = "Authentication"
    )
    @DeleteMapping("posts/{postId}/comments/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteComment(@PathVariable(name = "postId") long postId,
                                @PathVariable(name = "id") long id) {
        commentService.deleteComment(postId, id);
        return "Comment deleted successfully";
    }
}
