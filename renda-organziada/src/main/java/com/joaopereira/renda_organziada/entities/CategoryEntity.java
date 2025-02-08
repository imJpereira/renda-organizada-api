package com.joaopereira.renda_organziada.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "categories_tb")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID categoryId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "plan_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PlanEntity plan;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<ExpenseEntity> expenses;

    @Column(length = 100, nullable = false)
    private String description;

    @Column(columnDefinition = "DECIMAL(15,2)")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#,##0.00")
    private BigDecimal targetValue;

    @Column(columnDefinition = "DECIMAL(15,2)")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#,##0.00")
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

    public List<ExpenseEntity> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<ExpenseEntity> expenses) {
        this.expenses = expenses;
    }
}
