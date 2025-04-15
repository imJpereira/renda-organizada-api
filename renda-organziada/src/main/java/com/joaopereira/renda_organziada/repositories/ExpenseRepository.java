package com.joaopereira.renda_organziada.repositories;

import com.joaopereira.renda_organziada.entities.CategoryEntity;
import com.joaopereira.renda_organziada.entities.ExpenseEntity;
import com.joaopereira.renda_organziada.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, UUID> {

    List<ExpenseEntity> findByUser(UserEntity user);

    List<ExpenseEntity> findByCategory(CategoryEntity categoryEntity);

    List<ExpenseEntity> findByCategory_CategoryId(UUID categoryId);
}
