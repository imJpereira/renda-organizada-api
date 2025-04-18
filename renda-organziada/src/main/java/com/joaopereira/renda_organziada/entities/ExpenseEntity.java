package com.joaopereira.renda_organziada.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


@Entity
@Table(name = "expenses_tb")
public class ExpenseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID expenseId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true)
    private CategoryEntity category;

    @Column(columnDefinition = "DECIMAL(15,2)", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#,##0.00")
    private BigDecimal value;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate expenseDate;

    @Column(length = 100)
    private String description;

    //GETTERS AND SETTERS

    public UUID getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(UUID expenseId) {
        this.expenseId = expenseId;
    }

    public com.joaopereira.renda_organziada.entities.UserEntity getUser() {
        return user;
    }

    public void setUser(com.joaopereira.renda_organziada.entities.UserEntity user) {
        this.user = user;
    }

    public com.joaopereira.renda_organziada.entities.CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(com.joaopereira.renda_organziada.entities.CategoryEntity category) {
        this.category = category;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
