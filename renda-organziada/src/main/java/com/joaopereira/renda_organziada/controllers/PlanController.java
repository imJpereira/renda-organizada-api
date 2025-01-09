package com.joaopereira.renda_organziada.controllers;

import com.joaopereira.renda_organziada.dtos.PlanDTO;
import com.joaopereira.renda_organziada.entities.PlanEntity;
import com.joaopereira.renda_organziada.services.PlanService;
import com.joaopereira.renda_organziada.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

        var user = userService.findAll().get(0);
        newPlan.setUser_fk(user);

        planService.save(newPlan);
        return ResponseEntity.ok("Plano criado com sucesso!");
    }

    @GetMapping("/all")
    public ResponseEntity<List<PlanEntity>> findAll() throws Exception {
        var plans = planService.findAllSortedByDate();
        return ResponseEntity.ok(plans);
    }
}
