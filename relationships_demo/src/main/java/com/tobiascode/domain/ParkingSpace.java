package com.tobiascode.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ParkingSpace {
    @Id
    @GeneratedValue
    private Long id;

    private String number;

    @OneToOne(mappedBy = "parkingSpace")
    private Employee employee;

    public ParkingSpace(){}
    public ParkingSpace(String number){
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
