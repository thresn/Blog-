package com.BlogProject.Blog.services.Impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.BlogProject.Blog.domain.Category;
import com.BlogProject.Blog.repositories.CategoryRepository;
import com.BlogProject.Blog.services.CategoryService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> listCategories() {
        return categoryRepository.findAllWithPostCount();
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {
        if (categoryRepository.existsByNameIgnoreCase(category.getName())) {
            throw new IllegalArgumentException("Bu category zaten mevcut:" + category);
        }
        return categoryRepository.save(category);

    }

    @Override
    public void deleteCategory(UUID id) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isPresent()) { // Optional dan sonra gerçekten nesne var mı kontrolu?
            if (!category.get().getPosts().isEmpty()) { // category.getPosts().size() diyemem önde optionaldan gelen
                                                        // paketi açmalıyım. get ile sonra o category nesnesi olacak
                throw new IllegalStateException("Bu categorye sahip post mevcut");
            }
        }

        categoryRepository.deleteById(id);
    }

    @Override
    public Category getCategoryById(UUID id) {
        return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("category bulunamadı"));
    }

}
