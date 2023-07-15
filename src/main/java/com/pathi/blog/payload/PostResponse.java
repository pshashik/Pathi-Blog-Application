package com.pathi.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private long pageNo;
    private long pageSize;
    private long totalPages;
    private long totalElements;
    private boolean last;
    private List<PostDto> posts;
}
