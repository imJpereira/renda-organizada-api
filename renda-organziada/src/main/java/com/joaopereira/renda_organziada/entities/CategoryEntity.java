package com.joaopereira.renda_organziada.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "categories_tb")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID category_id;

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private PlanEntity plan_fk;

    @Column(length = 100, nullable = false)
    private String description;

    @Column(columnDefinition = "DECIMAL(15,2)")
    private BigDecimal target_value;

    @Column(columnDefinition = "DECIMAL(15,2)")
    private BigDecimal actual_value;

    public UUID getCategory_id() {
        return category_id;
    }

    public void setCategory_id(UUID category_id) {
        this.category_id = category_id;
    }

    public PlanEntity getPlan_fk() {
        return plan_fk;
    }

    public void setPlan_fk(PlanEntity plan_fk) {
        this.plan_fk = plan_fk;
    }

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
