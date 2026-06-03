package com.BlogProject.Blog.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCategoryRequest {
    
    @NotBlank(message = "Category isim kısımı boş bırakılamaz")
    @Size(max = 50, min = 2 ,message = "Category isim uzunluğu {min} ve {max} aralığında olmalı")
    private String name;
}
