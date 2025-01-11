package com.joaopereira.renda_organziada.dtos;

import com.joaopereira.renda_organziada.entities.PlanEntity;

import java.math.BigDecimal;
import java.util.UUID;

public class CategoryDTO {

    //Fazer plan_fk  --> Pesquisar sobre autenticação

    private String description;
    private BigDecimal target_value;
    private BigDecimal actual_value;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTarget_value() {
        return target_value;
    }

    public void setTarget_value(BigDecimal target_value) {
        this.target_value = target_value;
    }

    public BigDecimal getActual_value() {
        return actual_value;
    }

    public void setActual_value(BigDecimal actual_value) {
        this.actual_value = actual_value;
    }
}
