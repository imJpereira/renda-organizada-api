package com.joaopereira.renda_organziada.services;

import com.joaopereira.renda_organziada.dtos.PlanDTO;
import com.joaopereira.renda_organziada.entities.ExpenseEntity;
import com.joaopereira.renda_organziada.entities.PlanEntity;
import com.joaopereira.renda_organziada.entities.UserEntity;
import com.joaopereira.renda_organziada.repositories.CategoryRepository;
import com.joaopereira.renda_organziada.repositories.ExpenseRepository;
import com.joaopereira.renda_organziada.repositories.PlanRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class PlanService {
    final private PlanRepository planRepository;
    final private ExpenseRepository expenseRepository;
    final private CategoryService categoryService;
    
    
    public PlanService(PlanRepository planRepository, ExpenseRepository expenseRepository, CategoryService categoryService) {
        this.planRepository = planRepository;
        this.expenseRepository = expenseRepository;
        this.categoryService = categoryService;
     
    }

    public PlanEntity save(PlanEntity planEntity) throws Exception {

        if (planEntity.getUser() == null || planEntity.getUser().getUserId() == null ) {
            throw new IllegalArgumentException("Usuário Inválido");
        }
        if (planEntity.getStartDate() == null) {
            throw new IllegalArgumentException("Data inicial inválida");
        }
        if (planEntity.getFinalDate() == null ||  planEntity.getFinalDate().isBefore(planEntity.getStartDate())) {
            throw new IllegalArgumentException("Data final inválida");
        }
        if (planEntity.getInitialCapital() == null || planEntity.getInitialCapital().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Capital inválido");
        }

        planRepository.save(planEntity);
        categoryService.createBaseCategory(planEntity);

        return planEntity;
    }

    public List<PlanEntity> findByAllUser(UserEntity userEntity) throws Exception {
        return planRepository.findByUser(userEntity);
    }

    public PlanEntity findById(UUID id) throws Exception {
        return planRepository.findById(id).orElseThrow(() -> new Exception("Este plano não existe"));
    }

    public void deleteById(UUID plan_id) throws Exception {

        PlanEntity plan = planRepository.findById(plan_id)
                .orElseThrow(() -> new IllegalArgumentException("O Plano com id \""+plan_id+"\" não existe")
        );

        plan.getCategories().forEach(categoryEntity -> {

            List<ExpenseEntity> expenses = expenseRepository.findByCategory_CategoryId(categoryEntity.getCategoryId());

            expenses.forEach(expense -> expense.setCategory(null));

            expenseRepository.saveAll(expenses);
        });

        planRepository.deleteById(plan_id);
    }

    public PlanEntity updatePlan(UUID planId, PlanDTO planDTO) throws Exception {

        var plan = findById(planId);

        if (planDTO.getTitle() != null) {
            plan.setTitle(planDTO.getTitle());
        }

        if (planDTO.getInitialCapital() != null) {
           categoryService.updateBaseCategory(plan, planDTO.getInitialCapital());        
           
           plan.setInitialCapital(planDTO.getInitialCapital());
        
        }

        if (planDTO.getStartDate() != null) {
            plan.setStartDate(planDTO.getStartDate());
        }

        if (planDTO.getFinalDate() != null) {
            plan.setFinalDate(planDTO.getFinalDate());
        }

        return save(plan);
    }
}
