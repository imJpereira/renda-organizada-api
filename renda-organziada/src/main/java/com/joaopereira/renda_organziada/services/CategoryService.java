package com.joaopereira.renda_organziada.services;

import com.joaopereira.renda_organziada.entities.CategoryEntity;
import com.joaopereira.renda_organziada.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    final private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryEntity> findAll() throws Exception {
        return categoryRepository.findAll();
    }

    public CategoryEntity create(CategoryEntity newCategory) throws Exception {

        return categoryRepository.save(newCategory);
    }
}
