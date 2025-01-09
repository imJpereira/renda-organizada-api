package com.joaopereira.renda_organziada.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "plans_tb")
public class PlanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID plan_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user_fk;

    @Column(length = 100, nullable = false)
    private String plan_title;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate start_date;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate final_date;

    @Column(columnDefinition = "DECIMAL(15,2)")
    private BigDecimal initial_capital;

    @Column(columnDefinition = "DECIMAL(15,2)")
    private BigDecimal total_spent;

    public UUID getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(UUID plan_id) {
        this.plan_id = plan_id;
    }

    public UserEntity getUser_fk() {
        return user_fk;
    }

    public void setUser_fk(UserEntity user_fk) {
        this.user_fk = user_fk;
    }

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
