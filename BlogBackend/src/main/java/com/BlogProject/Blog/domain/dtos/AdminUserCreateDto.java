package com.BlogProject.Blog.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AdminUserCreateDto {
    private String name;
    private String email;
    private String password;
}
