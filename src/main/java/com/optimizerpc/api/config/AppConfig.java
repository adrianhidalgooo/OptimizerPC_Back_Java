package com.optimizerpc.api.config;

import com.optimizerpc.api.entity.AppUser;
import com.optimizerpc.api.entity.Article;
import com.optimizerpc.api.entity.Category;
import com.optimizerpc.api.repository.AppUserRepository;
import com.optimizerpc.api.repository.ArticleRepository;
import com.optimizerpc.api.repository.CategoryRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner initData(
            AppUserRepository userRepository,
            CategoryRepository categoryRepository,
            ArticleRepository articleRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            if (!userRepository.existsByUsername("admin")) {
                AppUser admin = new AppUser();
                admin.setName("Administrador");
                admin.setEmail("admin@optimizerpc.local");
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                userRepository.save(admin);
            }

            createCategory(categoryRepository, "Optimizaciones", "OPTIMIZATION");
            createCategory(categoryRepository, "Limpiezas", "CLEANING");
            createCategory(categoryRepository, "Reparaciones", "REPAIR");

            if (articleRepository.count() == 0) {
                Category optimization = categoryRepository.findByType("OPTIMIZATION").orElseThrow();
                Category cleaning = categoryRepository.findByType("CLEANING").orElseThrow();
                Category repair = categoryRepository.findByType("REPAIR").orElseThrow();

                Article first = new Article();
                first.setName("Optimizacion basica");
                first.setImage("./img/logos/MinimalistModernSimpleLogo.png");
                first.setPrice(29.99);
                first.setCategory(optimization);

                Article second = new Article();
                second.setName("Limpieza interna");
                second.setImage("./img/logos/MinimalistModernSimpleLogo.png");
                second.setPrice(39.99);
                second.setCategory(cleaning);

                Article third = new Article();
                third.setName("Revision y reparacion");
                third.setImage("./img/logos/MinimalistModernSimpleLogo.png");
                third.setPrice(49.99);
                third.setCategory(repair);

                articleRepository.saveAll(List.of(first, second, third));
            }
        };
    }

    private void createCategory(CategoryRepository repository, String name, String type) {
        if (repository.findByType(type).isEmpty()) {
            Category category = new Category();
            category.setName(name);
            category.setType(type);
            repository.save(category);
        }
    }
}
