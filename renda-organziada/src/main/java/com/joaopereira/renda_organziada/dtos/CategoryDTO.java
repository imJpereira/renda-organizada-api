package com.joaopereira.renda_organziada.dtos;

import com.joaopereira.renda_organziada.entities.PlanEntity;

import java.math.BigDecimal;
import java.util.UUID;

public class CategoryDTO {
    private String description;
    private BigDecimal targetValue;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(BigDecimal targetValue) {
        this.targetValue = targetValue;
    }

}
