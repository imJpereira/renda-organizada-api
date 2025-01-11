package com.joaopereira.renda_organziada.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "categories_tb")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID categoryId;

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private PlanEntity plan;

    @Column(length = 100, nullable = false)
    private String description;

    @Column(columnDefinition = "DECIMAL(15,2)")
    private BigDecimal targetValue;

    @Column(columnDefinition = "DECIMAL(15,2)")
    private BigDecimal actualValue;


    // GETTERS AND SETTERS


    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public PlanEntity getPlan() {
        return plan;
    }

    public void setPlan(PlanEntity plan) {
        this.plan = plan;
    }

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

    public BigDecimal getActualValue() {
        return actualValue;
    }

    public void setActualValue(BigDecimal actualValue) {
        this.actualValue = actualValue;
    }
}
