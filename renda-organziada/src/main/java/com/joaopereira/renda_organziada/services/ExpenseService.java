package com.joaopereira.renda_organziada.services;

import com.joaopereira.renda_organziada.entities.ExpenseEntity;
import com.joaopereira.renda_organziada.repositories.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;


@Service
public class ExpenseService {
    private ExpenseRepository expenseRepository;

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

    public List<ExpenseEntity> findAllSortedByDate() throws Exception {
        return expenseRepository.findAllSortedByDateDesc();
    }

    public List<ExpenseEntity> findByCategory(UUID category_id) throws Exception {
        return expenseRepository.findByCategory_CategoryId(category_id);
    }

    public void delete(UUID expense_id) throws Exception {
        if (!expenseRepository.existsById(expense_id)) {
            throw new IllegalArgumentException("A Despesa com id \""+expense_id+"\" não existe");
        }

        expenseRepository.deleteById(expense_id);
    }

}
