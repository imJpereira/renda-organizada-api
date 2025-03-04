package com.joaopereira.renda_organziada.services;

import com.joaopereira.renda_organziada.dtos.CategoryDTO;
import com.joaopereira.renda_organziada.dtos.CategorySumDTO;
import com.joaopereira.renda_organziada.entities.CategoryEntity;
import com.joaopereira.renda_organziada.entities.ExpenseEntity;
import com.joaopereira.renda_organziada.entities.PlanEntity;
import com.joaopereira.renda_organziada.enums.CategoryType;
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

    public CategoryEntity save(CategoryEntity category) throws Exception {
        if (category.getType().equals(CategoryType.BASE)) return categoryRepository.save(category);

        CategoryEntity baseCategory = categoryRepository.findFirstByType(CategoryType.BASE);
        baseCategory.setTargetValue(baseCategory.getTargetValue().subtract(category.getTargetValue()));

        if (baseCategory.getTargetValue().compareTo(BigDecimal.ZERO) > 0)
            categoryRepository.save(baseCategory);
        else
            categoryRepository.deleteById(baseCategory.getCategoryId());

        return categoryRepository.save(category);
    }

    public List<CategoryEntity> findCategories(UUID planId) throws Exception {
        return categoryRepository.findByPlan_PlanId(planId);
    }

    public CategoryEntity findByCategoryId(UUID categoryId) throws Exception {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }

    public void delete(UUID categoryId) throws Exception {

        List<ExpenseEntity> expenses = expenseRepository.findByCategory_CategoryId(categoryId);

        expenses.forEach(expense -> expense.setCategory(null));
        expenseRepository.saveAll(expenses);

        categoryRepository.deleteById(categoryId);
    }

    public CategoryEntity updateCategory(UUID categoryId, CategoryDTO categoryDTO) throws Exception {
        var category = this.findByCategoryId(categoryId);

        if (categoryDTO.getDescription() != null) category.setDescription(categoryDTO.getDescription());

        if (categoryDTO.getTargetValue() != null) category.setTargetValue(categoryDTO.getTargetValue());

        return categoryRepository.save(category);
    }

    public void createBaseCategory(PlanEntity planEntity) {
        CategoryEntity baseCategory = new CategoryEntity();
        baseCategory.setPlan(planEntity);
        baseCategory.setDescription("Geral");
        baseCategory.setType(CategoryType.BASE);
        baseCategory.setTargetValue(planEntity.getInitialCapital());
        baseCategory.setActualValue(BigDecimal.ZERO);

        categoryRepository.save(baseCategory);
    }

    public CategoryEntity findByBaseCategory() throws Exception {
        return categoryRepository.findFirstByType(CategoryType.BASE);
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
        categorySumDTO.setTargetValueSum(totalTargetValue);
        categorySumDTO.setBalance(totalTargetValue.subtract(totalActualValue));

        return categorySumDTO;
    }

}
