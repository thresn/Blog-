package com.BlogProject.Blog.services;

import java.util.List;
import java.util.UUID;

import com.BlogProject.Blog.domain.Category;

//service direkt entity ile uğraşır o yüzden dto değil düz category olqarak kullanacağım. METHOD İÇLERİNDE DE
public interface CategoryService {
    List<Category> listCategories();
    Category createCategory(Category category);
    void deleteCategory(UUID id);
    Category getCategoryById(UUID id);
}
