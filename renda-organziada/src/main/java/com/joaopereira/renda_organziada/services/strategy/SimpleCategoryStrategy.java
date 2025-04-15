package com.joaopereira.renda_organziada.services.strategy;

import com.joaopereira.renda_organziada.entities.CategoryEntity;
import com.joaopereira.renda_organziada.entities.PlanEntity;
import com.joaopereira.renda_organziada.enums.CategoryType;
import com.joaopereira.renda_organziada.repositories.CategoryRepository;
import com.joaopereira.renda_organziada.services.BaseCategoryService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SimpleCategoryStrategy implements CategoryStrategy{

    private final CategoryRepository categoryRepository;
    private final BaseCategoryService baseCategoryService;

    public SimpleCategoryStrategy(CategoryRepository categoryRepository, BaseCategoryService baseCategoryService) {
        this.categoryRepository = categoryRepository;
        this.baseCategoryService = baseCategoryService;
    }

    @Override
    public CategoryEntity create(PlanEntity planEntity, CategoryEntity categoryEntity) {
        baseCategoryService.adjustTargetValue(planEntity, categoryEntity.getTargetValue());
        return categoryRepository.save(categoryEntity);
    }

}

