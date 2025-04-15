package com.joaopereira.renda_organziada.services;

import com.joaopereira.renda_organziada.dtos.ExpenseDTO;
import com.joaopereira.renda_organziada.entities.CategoryEntity;
import com.joaopereira.renda_organziada.entities.ExpenseEntity;
import com.joaopereira.renda_organziada.entities.UserEntity;
import com.joaopereira.renda_organziada.enums.CategoryType;
import com.joaopereira.renda_organziada.repositories.CategoryRepository;
import com.joaopereira.renda_organziada.repositories.ExpenseRepository;
import com.joaopereira.renda_organziada.repositories.PlanRepository;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Service
public class ExpenseService {
    final private ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final PlanRepository planRepository;


    public ExpenseService(ExpenseRepository expenseRepository, CategoryRepository categoryRepository, PlanRepository planRepository) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
        this.planRepository = planRepository;
    }

    public ExpenseEntity save(ExpenseEntity expense) throws Exception {

        if (expense.getUser() == null || expense.getUser().getUserId() == null) {
            throw new IllegalArgumentException("Invalid username");
        }

        if (expense.getValue() == null) {
            throw new IllegalArgumentException("Invalid Value: value is null");
        }

        if (expense.getValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid Value: less or equal to zero");
        }

        var plan = expense.getCategory().getPlan();
        plan.setTotalSpent(plan.getTotalSpent().add(expense.getValue()));

        planRepository.save(plan);
        expenseRepository.save(expense);

        return expense;
    }

    public List<ExpenseEntity> findAllByUser(UserEntity user) throws Exception {
        return expenseRepository.findByUser(user);
    }

    public List<ExpenseEntity> findByCategory(UUID category_id) throws Exception {
        return expenseRepository.findByCategory_CategoryId(category_id);
    }

    public ExpenseEntity findById(UUID id) throws Exception {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("The expense \""+id+"\" doesn't exist"));
    }

    public void delete(UUID id) throws Exception {
        if (!expenseRepository.existsById(id)) {
            throw new IllegalArgumentException("The expense \""+id+"\" doesn't exist");
        }

        expenseRepository.deleteById(id);
    }

    public ExpenseEntity updateExpenses(UUID expenseId, ExpenseDTO expenseDTO) throws Exception {
        var expense = this.findById(expenseId);

        if (expenseDTO.getExpenseDate() != null) expense.setExpenseDate(expenseDTO.getExpenseDate());

        if (expenseDTO.getDescription() != null) expense.setDescription(expenseDTO.getDescription());

        if (expenseDTO.getValue() != null) expense.setValue(expenseDTO.getValue());

        if (expenseDTO.getCategory() != null) {
            var category = categoryRepository.findById(expenseDTO.getCategory())
                    .orElseThrow(() -> new IllegalArgumentException("Category ID doesn't exist"));
            expense.setCategory(category);
        }

        return expenseRepository.save(expense);
    }

}
