package com.pathi.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private long id;

    @NotEmpty(message = "name should not be null or empty.")
    @Size(min=3,message = "name should have at least 3 characters.")
    private String name;

    @NotEmpty(message = "email should not be null or empty.")
    @Email
    private String email;

    @NotEmpty(message = "body should not be null or empty.")
    private String body;
}
