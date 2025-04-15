package com.joaopereira.renda_organziada.services;

import com.joaopereira.renda_organziada.dtos.CategoryDTO;
import com.joaopereira.renda_organziada.entities.CategoryEntity;
import com.joaopereira.renda_organziada.entities.ExpenseEntity;
import com.joaopereira.renda_organziada.entities.PlanEntity;
import com.joaopereira.renda_organziada.enums.CategoryType;
import com.joaopereira.renda_organziada.repositories.CategoryRepository;
import com.joaopereira.renda_organziada.repositories.ExpenseRepository;
import com.joaopereira.renda_organziada.repositories.PlanRepository;
import com.joaopereira.renda_organziada.services.strategy.CategoryStrategyFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {
    final private CategoryRepository categoryRepository;
    final private ExpenseRepository expenseRepository;
    private final CategoryStrategyFactory categoryStrategyFactory;
    private final BaseCategoryService baseCategoryService;

    public CategoryService(CategoryRepository categoryRepository, ExpenseRepository expenseRepository, CategoryStrategyFactory categoryStrategyFactory, BaseCategoryService baseCategoryService) {
        this.categoryRepository = categoryRepository;
        this.expenseRepository = expenseRepository;
        this.categoryStrategyFactory = categoryStrategyFactory;
        this.baseCategoryService = baseCategoryService;
    }

    public CategoryEntity save(CategoryEntity category) throws Exception {
        return categoryRepository.save(category);
    }

    public CategoryEntity create(PlanEntity planEntity, CategoryEntity categoryEntity) throws Exception {
        return categoryStrategyFactory
                .getCategoryStrategyMap(categoryEntity.getType())
                .create(planEntity, categoryEntity);
    }

    public List<CategoryEntity> findCategories(UUID planId) throws Exception {
        return categoryRepository.findByPlan_PlanId(planId);
    }

    public CategoryEntity findByCategoryId(UUID categoryId) throws Exception {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }

    public void delete(CategoryEntity categoryEntity) throws Exception {

        List<ExpenseEntity> expenses = expenseRepository.findByCategory(categoryEntity);
        var baseCategory = baseCategoryService.findBaseCategory(categoryEntity.getPlan());

        expenses.forEach(expense -> expense.setCategory(baseCategory));
        expenseRepository.saveAll(expenses);

        baseCategoryService.adjustTargetValue(categoryEntity.getPlan(), categoryEntity.getTargetValue().negate());
        baseCategoryService.adjustActualValue(categoryEntity.getPlan(), categoryEntity.getActualValue());

        categoryRepository.delete(categoryEntity);
    }

    public CategoryEntity updateCategory(UUID categoryId, CategoryDTO categoryDTO) throws Exception {
        var category = this.findByCategoryId(categoryId);

        if (categoryDTO.getDescription() != null) category.setDescription(categoryDTO.getDescription());

        if (categoryDTO.getTargetValue() != null) category.setTargetValue(categoryDTO.getTargetValue());

        return categoryRepository.save(category);
    }


}
