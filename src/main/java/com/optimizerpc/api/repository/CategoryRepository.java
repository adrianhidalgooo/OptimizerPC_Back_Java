package com.optimizerpc.api.repository;

import com.optimizerpc.api.entity.Category;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    Optional<Category> findByType(String type);
}
