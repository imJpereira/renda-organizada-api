package com.joaopereira.renda_organziada.services;

import com.joaopereira.renda_organziada.dtos.CategorySumDTO;
import com.joaopereira.renda_organziada.entities.CategoryEntity;
import com.joaopereira.renda_organziada.entities.ExpenseEntity;
import com.joaopereira.renda_organziada.repositories.CategoryRepository;
import com.joaopereira.renda_organziada.repositories.ExpenseRepository;
import com.joaopereira.renda_organziada.repositories.PlanRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {
    final private CategoryRepository categoryRepository;
    final private ExpenseRepository expenseRepository;

    public CategoryService(CategoryRepository categoryRepository, ExpenseRepository expenseRepository) {
        this.categoryRepository = categoryRepository;
        this.expenseRepository = expenseRepository;
    }

    public CategoryEntity save(CategoryEntity newCategory) throws Exception {
        return categoryRepository.save(newCategory);
    }

    public List<CategoryEntity> findCategories(UUID planId) throws Exception {
        return categoryRepository.findByPlan_PlanId(planId);
    }

    public CategoryEntity findByCategoryId(UUID categoryId) throws Exception {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));
    }

    public void delete(UUID categoryId) throws Exception {

        List<ExpenseEntity> expenses = expenseRepository.findByCategory_CategoryId(categoryId);

        expenses.forEach(expense -> expense.setCategory(null));
        expenseRepository.saveAll(expenses);

        categoryRepository.deleteById(categoryId);
    }

    public CategorySumDTO sumCategoryValues(UUID plan_id) throws Exception {
        List<CategoryEntity> categories = categoryRepository.findByPlan_PlanId(plan_id);

        BigDecimal totalActualValue = BigDecimal.ZERO;
        BigDecimal totalTargetValue = BigDecimal.ZERO;

        for (CategoryEntity categoryEntity : categories) {
            totalActualValue = totalActualValue.add(categoryEntity.getActualValue());
            totalTargetValue = totalTargetValue.add(categoryEntity.getTargetValue());
        }

        CategorySumDTO categorySumDTO = new CategorySumDTO();
        categorySumDTO.setActualValueSum(totalActualValue);
        categorySumDTO.setTagetValueSum(totalTargetValue);
        categorySumDTO.setBalance(totalTargetValue.subtract(totalActualValue));

        return categorySumDTO;
    }

}
