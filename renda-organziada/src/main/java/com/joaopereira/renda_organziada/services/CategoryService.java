package com.joaopereira.renda_organziada.services;

import com.joaopereira.renda_organziada.entities.CategoryEntity;
import com.joaopereira.renda_organziada.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {
    final private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryEntity save(CategoryEntity newCategory) throws Exception {
        return categoryRepository.save(newCategory);
    }

    public List<CategoryEntity> findCategories(UUID planId) throws Exception {
        return categoryRepository.findByPlan_PlanId(planId);
    }

    public CategoryEntity findByCategoryId(UUID cateogryId) throws Exception {
        return categoryRepository.findById(cateogryId).orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));

    }

    public void delete(UUID categoryId) throws Exception {
        categoryRepository.deleteById(categoryId);
    }

}
