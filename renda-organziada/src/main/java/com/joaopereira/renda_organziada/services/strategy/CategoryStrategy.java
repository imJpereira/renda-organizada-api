package com.joaopereira.renda_organziada.services.strategy;

import com.joaopereira.renda_organziada.entities.CategoryEntity;
import com.joaopereira.renda_organziada.entities.PlanEntity;

public interface CategoryStrategy {

    CategoryEntity create( PlanEntity planEntity, CategoryEntity categoryEntity);


}
