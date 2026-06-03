package com.BlogProject.Blog.domain.dtos;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.BlogProject.Blog.enums.PostStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePostRequestDto {

    @NotBlank(message = "başlık gerekli")
    @Size(min = 10, max = 30, message = "uzunluk {min} ile {max} aralığında olmalıdır")
    private String title;

    @NotBlank(message = "içerik gerekli")
    @Size(min = 10, max = 500, message = "uzunluk {min} ile {max} aralığında olmalıdır")
    private String content;

    @NotNull(message = "category belirtilmeli")
    private UUID categoryId;

    @Builder.Default
    @Size(max = 10, message = "uzunluk {max} olmalıdır")
    private Set<UUID> tagsId = new HashSet<>();

    @NotNull(message = "status belirtilmeli")
    private PostStatus status;
}
