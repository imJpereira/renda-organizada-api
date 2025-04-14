package com.joaopereira.renda_organziada.controllers;

import com.joaopereira.renda_organziada.dtos.PlanDTO;
import com.joaopereira.renda_organziada.entities.CategoryEntity;
import com.joaopereira.renda_organziada.entities.PlanEntity;
import com.joaopereira.renda_organziada.entities.UserEntity;
import com.joaopereira.renda_organziada.services.PlanService;
import com.joaopereira.renda_organziada.services.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<PlanEntity> create(@RequestBody PlanDTO planDTO) throws Exception {
        var newPlan = new PlanEntity();
        BeanUtils.copyProperties(planDTO, newPlan);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity userEntity = userService.findByUsername(username);

        newPlan.setUser(userEntity);
        newPlan.setTotalSpent(BigDecimal.ZERO);

        var plan = planService.save(newPlan);
        return ResponseEntity.ok(plan);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PlanEntity>> findAllByUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity userEntity = userService.findByUsername(username);

        var plans = planService.findAllByUser(userEntity);
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/{plan_id}")
    public ResponseEntity<PlanEntity> findById(@PathVariable UUID plan_id) throws Exception {
        return ResponseEntity.ok(planService.findById(plan_id));
    }

    @DeleteMapping("/delete/{plan_id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID plan_id) throws Exception {
        planService.deleteById(plan_id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update/{plan_id}")
    public ResponseEntity<PlanEntity> updatePlan(@PathVariable UUID plan_id, @RequestBody PlanDTO planDTO) throws Exception {
        var updatedPlan = planService.updatePlan(plan_id, planDTO);
        return ResponseEntity.ok(updatedPlan);
    }

    @ExceptionHandler
    public ResponseEntity<String> handlerMethod(Exception ex) {
        String msg = ex.getMessage().replaceAll("\r\n", "");
        return ResponseEntity.badRequest().body(msg);
    }


}
