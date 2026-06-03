package com.BlogProject.Blog.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AdminStatsDto {
    private Long PostCount;
    private Long TagCount;
    private Long CategoryCount;
}
