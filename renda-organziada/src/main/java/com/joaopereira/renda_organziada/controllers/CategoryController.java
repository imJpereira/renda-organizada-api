package com.joaopereira.renda_organziada.controllers;

import com.joaopereira.renda_organziada.entities.CategoryEntity;
import com.joaopereira.renda_organziada.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    final private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/plan/{plan_id}")
    public ResponseEntity<List<CategoryEntity>> findCategories(@PathVariable UUID plan_id) throws Exception {
        var categories = categoryService.findCategories(plan_id);
        return ResponseEntity.ok(categories);
    }

    @ExceptionHandler
    public ResponseEntity<String> handlerMethod(Exception ex) {
        String msg = ex.getMessage().replaceAll("\r\n", "");
        return ResponseEntity.badRequest().body(msg);
    }

}
