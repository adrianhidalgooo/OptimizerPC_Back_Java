package com.optimizerpc.api.controller;

import com.optimizerpc.api.dto.ArticleRequest;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/v0")
public class ArticleController {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;

    public ArticleController(
            ArticleRepository articleRepository,
            CategoryRepository categoryRepository
    ) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/s/article")
    public List<Article> findAll(@RequestParam(required = false) String search) {
        if (search == null || search.isBlank()) {
            return articleRepository.findAllByOrderByCreatedAtDesc();
        }

        return articleRepository.findByNameContainingIgnoreCaseOrderByCreatedAtDesc(search);
    }

    @PostMapping("/article")
    @ResponseStatus(HttpStatus.CREATED)
    public Article create(
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

    @DeleteMapping("/article/{id}")
    public void delete(@PathVariable UUID id) {
        if (!articleRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado");
        }

        articleRepository.deleteById(id);
    }
}
