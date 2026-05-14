package com.optimizerpc.api.repository;

import com.optimizerpc.api.entity.Article;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, UUID> {

    List<Article> findAllByOrderByCreatedAtDesc();

    List<Article> findByNameContainingIgnoreCaseOrderByCreatedAtDesc(String name);
}
