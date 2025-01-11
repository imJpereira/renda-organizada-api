package com.joaopereira.renda_organziada.repositories;

import com.joaopereira.renda_organziada.entities.PlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlanRepository extends JpaRepository<PlanEntity, UUID> {

    @Query("SELECT p FROM PlanEntity p ORDER BY p.finalDate DESC")
    List<PlanEntity> findAllSortedByDateDesc();

}
