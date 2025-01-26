package com.joaopereira.renda_organziada.controllers;

import com.joaopereira.renda_organziada.dtos.PlanDTO;
import com.joaopereira.renda_organziada.entities.PlanEntity;
import com.joaopereira.renda_organziada.services.PlanService;
import com.joaopereira.renda_organziada.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/plans")
public class PlanController {
    final private PlanService planService;
    final private UserService userService;

    public PlanController(PlanService planService, UserService userService) {
        this.planService = planService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody PlanDTO planDTO) throws Exception {

        var newPlan = new PlanEntity();
        BeanUtils.copyProperties(planDTO, newPlan);

        newPlan.setUser(userService.findAll().get(0));
        newPlan.setTotalSpent(BigDecimal.ZERO);

        planService.save(newPlan);
        return ResponseEntity.ok("Plano criado com sucesso!");
    }

    @GetMapping("/all")
    public ResponseEntity<List<PlanEntity>> findAll() throws Exception {
        var plans = planService.findAllSortedByDate();
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/{plan_id}")
    public ResponseEntity<PlanEntity> findById(@PathVariable UUID plan_id) throws Exception {
        return ResponseEntity.ok(planService.findById(plan_id));
    }

    @DeleteMapping("/delete/{plan_id}")
    public ResponseEntity<String> deleteById(@PathVariable UUID plan_id) throws Exception {
        planService.deleteById(plan_id);
        return ResponseEntity.ok("Deletado com sucesso!");
    }

    @ExceptionHandler
    public ResponseEntity<String> handlerMethod(Exception ex) {
        String msg = ex.getMessage().replaceAll("\r\n", "");
        return ResponseEntity.badRequest().body(msg);
    }


}
