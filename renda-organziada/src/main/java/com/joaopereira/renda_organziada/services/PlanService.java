package com.joaopereira.renda_organziada.services;

import com.joaopereira.renda_organziada.entities.PlanEntity;
import com.joaopereira.renda_organziada.repositories.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlanService {
    final private PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public PlanEntity save(PlanEntity planEntity) throws Exception {
        //Fazer validações

        planRepository.save(planEntity);

        return planEntity;
    }

    public List<PlanEntity> findAllSortedByDate() throws Exception {
        return planRepository.findAllSortedByDateDesc();
    }

    public void deleteById(UUID plan_id) throws Exception {
        planRepository.deleteById(plan_id);
    }
}
