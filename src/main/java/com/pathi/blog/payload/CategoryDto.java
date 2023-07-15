package com.pathi.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    @NotEmpty(message = "Name should not be blank or empty!")
    @Size(min = 3,message = "Name should be more than 3 characters!")
    private String name;
    @NotEmpty(message = "Description should not be blank or empty!")
    private String description;
    private Set<PostDto> posts;
}
