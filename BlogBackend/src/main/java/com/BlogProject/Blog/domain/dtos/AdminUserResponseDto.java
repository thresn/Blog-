package com.BlogProject.Blog.domain.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AdminUserResponseDto {
    private UUID id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
}
