package com.BlogProject.Blog.domain.dtos;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CreateTagRequest {
    @NotEmpty(message = "En az 1 tag isimi gerekli")
    @Size(max = 10, message = "uzunluk sınırına ulaşıldı: {max} ")
    private Set<@Size(min = 2,max = 15, message = "uzunluk değeri hatası") @Pattern(regexp = "[\\w\\s-]+$", message = "tag karakteri uyumsuz") String> names;
}
