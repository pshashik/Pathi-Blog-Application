package com.pathi.blog.payload;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseDto {
    @Setter
    @Getter
    private String token;
    @Getter
    private String tokenType = "Bearer";
}
