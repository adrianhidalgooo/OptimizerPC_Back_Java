package com.optimizerpc.api.controller;

import com.optimizerpc.api.entity.Article;
import com.optimizerpc.api.entity.Category;
import com.optimizerpc.api.repository.ArticleRepository;
import com.optimizerpc.api.repository.CategoryRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ProductController {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;

    public ProductController(ArticleRepository articleRepository, CategoryRepository categoryRepository) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/v0/s/categories")
    public List<Category> findCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/v0/s/article")
    public List<Article> findArticles(@RequestParam(required = false) String search) {
        if (search == null || search.isBlank()) {
            return articleRepository.findAllByOrderByCreatedAtDesc();
        }

        return articleRepository.findByNameContainingIgnoreCaseOrderByCreatedAtDesc(search);
    }

    @PostMapping("/v0/article")
    @ResponseStatus(HttpStatus.CREATED)
    public Article createArticle(
            @RequestParam UUID categoryId,
            @RequestBody ArticleRequest request
    ) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria no encontrada"));

        Article article = new Article();
        article.setName(request.name());
        article.setImage(request.image());
        article.setPrice(request.price());
        article.setCategory(category);

        return articleRepository.save(article);
    }

    @DeleteMapping("/v0/article/{id}")
    public void deleteArticle(@PathVariable UUID id) {
        if (!articleRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado");
        }

        articleRepository.deleteById(id);
    }

    public record ArticleRequest(String name, String image, Double price) {
    }
}