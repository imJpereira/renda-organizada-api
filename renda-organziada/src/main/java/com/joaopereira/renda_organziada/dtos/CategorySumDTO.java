package com.joaopereira.renda_organziada.dtos;

import java.math.BigDecimal;

public class CategorySumDTO {
    private BigDecimal actualValueSum;
    private BigDecimal tagetValueSum;
    private BigDecimal balance;

    public BigDecimal getActualValueSum() {
        return actualValueSum;
    }

    public void setActualValueSum(BigDecimal actualValueSum) {
        this.actualValueSum = actualValueSum;
    }

    public BigDecimal getTagetValueSum() {
        return tagetValueSum;
    }

    public void setTagetValueSum(BigDecimal tagetValueSum) {
        this.tagetValueSum = tagetValueSum;
    }

public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
