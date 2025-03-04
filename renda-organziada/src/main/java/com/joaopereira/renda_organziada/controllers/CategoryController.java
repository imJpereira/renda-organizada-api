package com.joaopereira.renda_organziada.controllers;

import com.joaopereira.renda_organziada.dtos.CategoryDTO;
import com.joaopereira.renda_organziada.dtos.CategorySumDTO;
import com.joaopereira.renda_organziada.entities.CategoryEntity;
import com.joaopereira.renda_organziada.services.CategoryService;
import com.joaopereira.renda_organziada.services.PlanService;
import org.springframework.beans.BeanUtils;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.beans.Beans;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    final private CategoryService categoryService;
    final private PlanService planService;

    public CategoryController(CategoryService categoryService, PlanService planService) {
        this.categoryService = categoryService;
        this.planService = planService;
    }

    @GetMapping("/plan/{plan_id}")
    public ResponseEntity<List<CategoryEntity>> findCategories(@PathVariable UUID plan_id) throws Exception {
        List<CategoryEntity> categories = categoryService.findCategories(plan_id);

        categories.forEach(categoryEntity -> {
            BigDecimal actualValue = categoryEntity.getActualValue();
            NumberFormat.getCurrencyInstance().format(actualValue);

        });

        return ResponseEntity.ok(categories);
    }

    @PostMapping("create/plan/{plan_id}")
    public ResponseEntity<String> createCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable UUID plan_id) throws Exception {

        CategoryEntity newCategory = new CategoryEntity();
        BeanUtils.copyProperties(categoryDTO, newCategory);

        var plan = planService.findById(plan_id);
        newCategory.setPlan(plan);

        newCategory.setActualValue(BigDecimal.ZERO);

        categoryService.save(newCategory);

        return ResponseEntity.ok("Criado com sucesso");
    }

    @DeleteMapping("/delete/{category_id}")
    public ResponseEntity<String> delete(@PathVariable UUID category_id) throws Exception {
        categoryService.delete(category_id);
        return ResponseEntity.ok("Deletado com sucesso");
    }

    @PatchMapping("/update/{category_id}")
    public ResponseEntity<CategoryEntity> updateCategory(@PathVariable UUID category_id,@RequestBody CategoryDTO categoryDTO) throws Exception {
        var category = categoryService.updateCategory(category_id, categoryDTO);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/{plan_id}/totals")
    public CategorySumDTO sumCategoryValues(@PathVariable UUID plan_id) throws Exception {
        return categoryService.sumCategoryValues(plan_id);
    }

    @ExceptionHandler
    public ResponseEntity<String> handlerMethod(Exception ex) {
        String msg = ex.getMessage().replaceAll("\r\n", "");
        return ResponseEntity.badRequest().body(msg);
    }
}
