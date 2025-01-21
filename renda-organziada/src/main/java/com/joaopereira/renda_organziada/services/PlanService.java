package com.joaopereira.renda_organziada.services;

import com.joaopereira.renda_organziada.entities.PlanEntity;
import com.joaopereira.renda_organziada.repositories.PlanRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class PlanService {
    final private PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
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

        return planEntity;
    }

    public List<PlanEntity> findAllSortedByDate() throws Exception {
        return planRepository.findAllSortedByDateDesc();
    }

    public void deleteById(UUID plan_id) throws Exception {
        if (!planRepository.existsById(plan_id)) {
            throw new IllegalArgumentException("O Plano com id \""+plan_id+"\" não existe");
        }

        planRepository.deleteById(plan_id);
    }
}
