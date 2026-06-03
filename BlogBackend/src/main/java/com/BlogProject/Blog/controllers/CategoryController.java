package com.BlogProject.Blog.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BlogProject.Blog.domain.Category;
import com.BlogProject.Blog.domain.dtos.CategoryDto;
import com.BlogProject.Blog.domain.dtos.CreateCategoryRequest;
import com.BlogProject.Blog.mappers.CategoryMapper;
import com.BlogProject.Blog.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/categories")
@RequiredArgsConstructor
// controller kullanıcı ile konuşan katman. O yüzden dto ları kullanacağız.
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> listCategories() {
        List<Category> categories = categoryService.listCategories();
        List<CategoryDto> categoryDto = categories.stream().map(categoryMapper::toDto).toList();

        return ResponseEntity.ok(categoryDto);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest) {
        Category category = categoryMapper.toEntity(createCategoryRequest);
        Category savedCategory = categoryService.createCategory(category);
        CategoryDto dto = categoryMapper.toDto(savedCategory);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
