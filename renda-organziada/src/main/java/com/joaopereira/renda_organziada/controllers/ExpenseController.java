package com.joaopereira.renda_organziada.controllers;

import com.joaopereira.renda_organziada.dtos.ExpenseDTO;
import com.joaopereira.renda_organziada.entities.CategoryEntity;
import com.joaopereira.renda_organziada.entities.ExpenseEntity;
import com.joaopereira.renda_organziada.entities.UserEntity;
import com.joaopereira.renda_organziada.repositories.CategoryRepository;
import com.joaopereira.renda_organziada.services.*;
import com.joaopereira.renda_organziada.services.strategy.SimpleCategoryStrategy;
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
    private final CategoryRepository categoryRepository;
    private final BaseCategoryService baseCategoryService;
    private final SimpleCategoryStrategy simpleCategoryStrategy;

    public ExpenseController(ExpenseService expenseService, UserService userService, CategoryService categoryService, PlanService planService, CategoryRepository categoryRepository, BaseCategoryService baseCategoryService, SimpleCategoryStrategy simpleCategoryStrategy) {
        this.expenseService = expenseService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.planService = planService;
        this.categoryRepository = categoryRepository;
        this.baseCategoryService = baseCategoryService;
        this.simpleCategoryStrategy = simpleCategoryStrategy;
    }

    @PostMapping("/create")
    public ResponseEntity<ExpenseEntity> create(@RequestBody ExpenseDTO expenseDTO) throws Exception {
        ExpenseEntity newExpense = new ExpenseEntity();
        BeanUtils.copyProperties(expenseDTO, newExpense);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity userEntity = userService.findByUsername(username);

        newExpense.setUser(userEntity);

        var category = (expenseDTO.getCategory() != null)
                ? categoryService.findByCategoryId(expenseDTO.getCategory())
                : baseCategoryService.findBaseCategory( planService.findById(expenseDTO.getPlan()) );

        newExpense.setCategory(category);

        category.setActualValue(category.getActualValue().add(newExpense.getValue()));
        categoryService.save(category);

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
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {

        ExpenseEntity expense = expenseService.findById(id);
        if (expense.getCategory() != null) {
            CategoryEntity category = categoryService.findByCategoryId(expense.getCategory().getCategoryId());
            category.setActualValue(category.getActualValue().subtract(expense.getValue()));
        }

        expenseService.delete(id);
        return ResponseEntity.noContent().build();
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
