package com.tobiascode.domain;

import javax.persistence.Entity;

@Entity
public class PartTimeEmployee extends CompanyEmployee {
    private float hourlyRate;

    public float getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(float hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public String toString() {
        return "PartTimeEmployee [id: " + getId() + " name: " + getName() + "]";
    }
}
