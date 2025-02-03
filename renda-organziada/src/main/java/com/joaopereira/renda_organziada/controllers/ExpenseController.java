package com.joaopereira.renda_organziada.controllers;

import com.joaopereira.renda_organziada.dtos.ExpenseDTO;
import com.joaopereira.renda_organziada.entities.ExpenseEntity;
import com.joaopereira.renda_organziada.services.CategoryService;
import com.joaopereira.renda_organziada.services.ExpenseService;
import com.joaopereira.renda_organziada.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;
    private final UserService userService;
    private final CategoryService categoryService;

    public ExpenseController(ExpenseService expenseService, UserService userService, CategoryService categoryService) {
        this.expenseService = expenseService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<ExpenseEntity> create(@RequestBody ExpenseDTO expenseDTO) throws Exception {

        ExpenseEntity newExpense = new ExpenseEntity();
        BeanUtils.copyProperties(expenseDTO, newExpense);

        //Fazer quando autenticação estiver pronta
        newExpense.setUser(userService.findAll().get(0));

        if (expenseDTO.getCategory() != null) {
            var category = categoryService.findByCategoryId(expenseDTO.getCategory());
            newExpense.setCategory(category);
        }

        expenseService.save(newExpense);

        return ResponseEntity.ok(newExpense);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ExpenseEntity>> findAll() throws Exception {
        var expenses = expenseService.findAllSortedByDate();
        return ResponseEntity.ok(expenses);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) throws Exception {
        expenseService.delete(id);

        return ResponseEntity.ok("Deletado com sucesso");
    }

    @GetMapping("/category/{category_id}")
    public ResponseEntity<List<ExpenseEntity>> findByCategory(@PathVariable UUID category_id) throws Exception
    {
        return ResponseEntity.ok(expenseService.findByCategory(category_id));
    }

    @ExceptionHandler
    public ResponseEntity<String> handlerMethod(Exception ex) {
        String msg = ex.getMessage().replaceAll("\r\n", "");
        return ResponseEntity.badRequest().body(msg);
    }

}
