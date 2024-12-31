package com.joaopereira.renda_organziada.dtos;


import java.time.LocalDate;
import java.util.UUID;
import lombok.*;

public class ExpenseDTO {
    private UUID category_fk;
    private double expense_value;
    private LocalDate expense_date;
    private String description;

    public UUID getCategory_fk() {
        return category_fk;
    }

    public void setCategory_fk(UUID category_fk) {
        this.category_fk = category_fk;
    }

    public double getExpense_value() {
        return expense_value;
    }

    public void setExpense_value(double expense_value) {
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
