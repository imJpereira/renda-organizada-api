package com.joaopereira.renda_organziada.controllers;

import com.joaopereira.renda_organziada.dtos.ExpenseDTO;
import com.joaopereira.renda_organziada.entities.CategoryEntity;
import com.joaopereira.renda_organziada.entities.ExpenseEntity;
import com.joaopereira.renda_organziada.entities.PlanEntity;
import com.joaopereira.renda_organziada.entities.UserEntity;
import com.joaopereira.renda_organziada.repositories.PlanRepository;
import com.joaopereira.renda_organziada.services.CategoryService;
import com.joaopereira.renda_organziada.services.ExpenseService;
import com.joaopereira.renda_organziada.services.PlanService;
import com.joaopereira.renda_organziada.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final PlanService planService;

    public ExpenseController(ExpenseService expenseService, UserService userService, CategoryService categoryService, PlanRepository planRepository, PlanService planService) {
        this.expenseService = expenseService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.planService = planService;
    }

    @PostMapping("/create")
    public ResponseEntity<ExpenseEntity> create(@RequestBody ExpenseDTO expenseDTO) throws Exception {
        ExpenseEntity newExpense = new ExpenseEntity();
        BeanUtils.copyProperties(expenseDTO, newExpense);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity userEntity = userService.findByUsername(username);

        newExpense.setUser(userEntity);

        CategoryEntity category;
        if (expenseDTO.getCategory() != null) {
            category = categoryService.findByCategoryId(expenseDTO.getCategory());
        } else {
            var plan = planService.findById(expenseDTO.getPlan());
            category = categoryService.findBaseCategory(plan);
        }
        newExpense.setCategory(category);

        expenseService.save(newExpense);

        return ResponseEntity.ok(newExpense);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ExpenseEntity>> findAllByUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity userEntity = userService.findByUsername(username);

        var expenses = expenseService.findAllByUser(userEntity);
        return ResponseEntity.ok(expenses);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) throws Exception {

        ExpenseEntity expense = expenseService.findById(id);
        if (expense.getCategory() != null) {
            CategoryEntity category = categoryService.findByCategoryId(expense.getCategory().getCategoryId());
            category.setActualValue(category.getActualValue().subtract(expense.getValue()));
        }

        expenseService.delete(id);
        return ResponseEntity.ok("Deletado com sucesso");
    }

    @GetMapping("/category/{category_id}")
    public ResponseEntity<List<ExpenseEntity>> findByCategory(@PathVariable UUID category_id) throws Exception
    {
        return ResponseEntity.ok(expenseService.findByCategory(category_id));
    }

    @PatchMapping("/update/{expense_id}")
    public ResponseEntity<ExpenseEntity> updateExpense(@PathVariable UUID expense_id, @RequestBody ExpenseDTO expenseDTO) throws Exception {
        var expense = expenseService.updateExpenses(expense_id, expenseDTO);
        return ResponseEntity.ok(expense);
    }

    @ExceptionHandler
    public ResponseEntity<String> handlerMethod(Exception ex) {
        String msg = ex.getMessage().replaceAll("\r\n", "");
        return ResponseEntity.badRequest().body(msg);
    }

}
