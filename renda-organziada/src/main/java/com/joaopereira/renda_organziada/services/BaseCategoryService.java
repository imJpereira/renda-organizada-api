package com.joaopereira.renda_organziada.services;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.joaopereira.renda_organziada.entities.CategoryEntity;
import com.joaopereira.renda_organziada.entities.PlanEntity;
import com.joaopereira.renda_organziada.enums.CategoryType;
import com.joaopereira.renda_organziada.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BaseCategoryService {

    private final CategoryRepository categoryRepository;

    public BaseCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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

    public void updateBaseCategory(PlanEntity planEntity, BigDecimal newValue) throws Exception {

        var baseCategory = this.findBaseCategory(planEntity);
        var valueChanged = newValue.subtract(planEntity.getInitialCapital());

        baseCategory.setTargetValue(baseCategory.getTargetValue().add(valueChanged));
        categoryRepository.save(baseCategory);
    }

    public CategoryEntity findBaseCategory(PlanEntity plan) throws Exception {
        return categoryRepository.findFirstByPlanAndType(plan, CategoryType.BASE);
    }

    public boolean existsBaseCategory(PlanEntity plan) throws Exception {
        return  categoryRepository.existsByPlanAndType(plan, CategoryType.BASE);
    }

    public void adjustTargetValue(PlanEntity plan, BigDecimal valueToSubtract) {
        CategoryEntity baseCategory = categoryRepository.findFirstByPlanAndType(plan, CategoryType.BASE);
        baseCategory.setTargetValue(baseCategory.getTargetValue().subtract(valueToSubtract));

        if (baseCategory.getTargetValue().compareTo(BigDecimal.ZERO) > 0)
            categoryRepository.save(baseCategory);
        else
            categoryRepository.deleteById(baseCategory.getCategoryId());
    }


}
