package com.joaopereira.renda_organziada.dtos;

import java.math.BigDecimal;

public class CategorySumDTO {
    private BigDecimal actualValueSum;
    private BigDecimal targetValueSum;
    private BigDecimal balance;

    public BigDecimal getActualValueSum() {
        return actualValueSum;
    }

    public void setActualValueSum(BigDecimal actualValueSum) {
        this.actualValueSum = actualValueSum;
    }

    public BigDecimal getTargetValueSum() {
        return targetValueSum;
    }

    public void setTargetValueSum(BigDecimal targetValueSum) {
        this.targetValueSum = targetValueSum;
    }

public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
