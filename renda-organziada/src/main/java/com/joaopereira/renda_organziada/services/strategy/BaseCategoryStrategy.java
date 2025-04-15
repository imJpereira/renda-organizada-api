package com.joaopereira.renda_organziada.services.strategy;

import com.joaopereira.renda_organziada.entities.CategoryEntity;
import com.joaopereira.renda_organziada.entities.PlanEntity;
import com.joaopereira.renda_organziada.enums.CategoryType;
import com.joaopereira.renda_organziada.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BaseCategoryStrategy implements CategoryStrategy {
    private final CategoryRepository categoryRepository;

    public BaseCategoryStrategy(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryEntity create(PlanEntity planEntity, CategoryEntity categoryEntity) {
        CategoryEntity baseCategory = new CategoryEntity();
        baseCategory.setPlan(planEntity);
        baseCategory.setDescription("Geral");
        baseCategory.setType(CategoryType.BASE);
        baseCategory.setTargetValue(planEntity.getInitialCapital());
        baseCategory.setActualValue(BigDecimal.ZERO);

        return categoryRepository.save(baseCategory);
    }

}
