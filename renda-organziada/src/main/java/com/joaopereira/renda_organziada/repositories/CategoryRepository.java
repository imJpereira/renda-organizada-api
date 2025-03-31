package com.joaopereira.renda_organziada.repositories;

import com.joaopereira.renda_organziada.entities.CategoryEntity;
import com.joaopereira.renda_organziada.entities.PlanEntity;
import com.joaopereira.renda_organziada.enums.CategoryType;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {

    List<CategoryEntity> findByPlan_PlanId(UUID planPlanId);

    CategoryEntity findFirstByPlanAndType(PlanEntity plan, CategoryType type);

    Boolean existsByPlanAndType(PlanEntity plan, CategoryType type);
}
