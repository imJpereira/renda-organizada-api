package com.joaopereira.renda_organziada.dtos;

import com.joaopereira.renda_organziada.entities.UserEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PlanDTO {
    private String plan_title;
    private LocalDate start_date;
    private LocalDate final_date;
    private BigDecimal initial_capital;
    private BigDecimal total_spent;

    public String getPlan_title() {
        return plan_title;
    }

    public void setPlan_title(String plan_title) {
        this.plan_title = plan_title;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getFinal_date() {
        return final_date;
    }

    public void setFinal_date(LocalDate final_date) {
        this.final_date = final_date;
    }

    public BigDecimal getInitial_capital() {
        return initial_capital;
    }

    public void setInitial_capital(BigDecimal initial_capital) {
        this.initial_capital = initial_capital;
    }

    public BigDecimal getTotal_spent() {
        return total_spent;
    }

    public void setTotal_spent(BigDecimal total_spent) {
        this.total_spent = total_spent;
    }
}
