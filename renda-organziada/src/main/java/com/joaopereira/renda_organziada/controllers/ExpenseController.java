package com.joaopereira.renda_organziada.controllers;

import com.joaopereira.renda_organziada.dtos.ExpenseDTO;
import com.joaopereira.renda_organziada.entities.ExpenseEntity;
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

    public ExpenseController(ExpenseService expenseService, UserService userService) {
        this.expenseService = expenseService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<ExpenseEntity> create(@RequestBody ExpenseDTO expenseDTO) throws Exception {

        ExpenseEntity newExpense = new ExpenseEntity();
        BeanUtils.copyProperties(expenseDTO, newExpense);

        //Quando a autenticação ficar pronta, trocar estes comandos pela autenticação
        var user = userService.findAll().get(0);
        newExpense.setUser_fk(user);

        expenseService.save(newExpense);

        return ResponseEntity.ok(newExpense);
    }

    @GetMapping("/all")
    public List<ExpenseEntity> findAll() throws Exception {
        return expenseService.findAllSortedByDate();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) throws Exception {
        expenseService.delete(id);

        return ResponseEntity.ok("Deletado com sucesso");
    }

    @ExceptionHandler
    public ResponseEntity<String> handlerMethod(Exception ex) {
        String msg = ex.getMessage().replaceAll("\r\n", "");
        return ResponseEntity.badRequest().body(msg);
    }

}
