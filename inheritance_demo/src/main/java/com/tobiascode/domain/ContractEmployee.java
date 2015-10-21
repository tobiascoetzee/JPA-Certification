package com.tobiascode.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
public class ContractEmployee extends Employee {
    private int dailyRate;
    private int term;

    public int getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(int dailyRate) {
        this.dailyRate = dailyRate;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public String toString() {
        return "ContractEmployee [id: " + getId() + " name: " + getName() + "]";
    }
}
