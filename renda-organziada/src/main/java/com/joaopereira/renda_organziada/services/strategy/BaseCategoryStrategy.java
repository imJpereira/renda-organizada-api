package com.joaopereira.renda_organziada.services.strategy;

import com.joaopereira.renda_organziada.entities.CategoryEntity;
import com.joaopereira.renda_organziada.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class BaseCategoryStrategy implements CategoryStrategy {
    private final CategoryRepository categoryRepository;

    public BaseCategoryStrategy(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryEntity save(CategoryEntity categoryEntity) {
        return categoryRepository.save(categoryEntity);
    }
}
