package com.joaopereira.renda_organziada.repositories;

import com.joaopereira.renda_organziada.entities.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, UUID> {

    @Query("SELECT e FROM ExpenseEntity e ORDER BY e.expense_date DESC ")
    List<ExpenseEntity> findAllSortedByDateDesc();
}
