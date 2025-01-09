package com.joaopereira.renda_organziada.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private UUID expense_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user_fk;

    private UUID category_fk;

    @Column(columnDefinition = "DECIMAL(15,2)", nullable = false)
    private BigDecimal expense_value;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate expense_date;

    @Column(length = 100)
    private String description;


    public UUID getExpense_id() {
        return expense_id;
    }

    public void setExpense_id(UUID expense_id) {
        this.expense_id = expense_id;
    }

    public UserEntity getUser_fk() {
        return user_fk;
    }

    public void setUser_fk(UserEntity user_fk) {
        this.user_fk = user_fk;
    }

    public UUID getCategory_fk() {
        return category_fk;
    }

    public void setCategory_fk(UUID category_fk) {
        this.category_fk = category_fk;
    }

    public BigDecimal getExpense_value() {
        return expense_value;
    }

    public void setExpense_value(BigDecimal expense_value) {
        this.expense_value = expense_value;
    }

    public LocalDate getExpense_date() {
        return expense_date;
    }

    public void setExpense_date(LocalDate expense_date) {
        this.expense_date = expense_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
