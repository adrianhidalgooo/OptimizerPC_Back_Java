package com.optimizerpc.api.controller;

import com.optimizerpc.api.entity.Category;
import com.optimizerpc.api.repository.CategoryRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v0")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/s/categories")
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
