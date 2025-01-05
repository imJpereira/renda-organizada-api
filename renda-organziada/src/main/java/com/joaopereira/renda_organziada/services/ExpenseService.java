package com.joaopereira.renda_organziada.services;

import com.joaopereira.renda_organziada.entities.ExpenseEntity;
import com.joaopereira.renda_organziada.repositories.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;


@Service
public class ExpenseService {
    private ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public ExpenseEntity save(ExpenseEntity newExpense) throws Exception {

        expenseRepository.save(newExpense);

        return newExpense;
    }

    public List<ExpenseEntity> findAllSortedByDate() throws Exception {
        return expenseRepository.findAllSortedByDateDesc();
    }

    public ExpenseEntity findById(UUID id) throws Exception {
        return expenseRepository.findById(id).orElseThrow(() -> new Exception("Registro não encontrado!"));
    }

    public void delete(UUID id) throws Exception {
        findById(id);
        expenseRepository.deleteById(id);
    }

}
