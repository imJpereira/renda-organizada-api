package com.joaopereira.renda_organziada.services;

import com.joaopereira.renda_organziada.entities.CategoryEntity;
import com.joaopereira.renda_organziada.entities.ExpenseEntity;
import com.joaopereira.renda_organziada.entities.UserEntity;
import com.joaopereira.renda_organziada.repositories.CategoryRepository;
import com.joaopereira.renda_organziada.repositories.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Service
public class ExpenseService {
    final private ExpenseRepository expenseRepository;


    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public ExpenseEntity save(ExpenseEntity expense) throws Exception {

        if (expense.getUser() == null || expense.getUser().getUserId() == null) {
            throw new IllegalArgumentException("Usuário inválido");
        }

        if (expense.getValue() == null) {
            throw new IllegalArgumentException("Valor inválido: valor nulo");
        }

        if (expense.getValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor inválido: menor ou igual à zero");
        }

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
                .orElseThrow(() -> new IllegalArgumentException("A Despesa com id \""+id+"\" não existe"));
    }

    public void delete(UUID id) throws Exception {
        if (!expenseRepository.existsById(id)) {
            throw new IllegalArgumentException("A Despesa com id \""+id+"\" não existe");
        }

        expenseRepository.deleteById(id);
    }

}
