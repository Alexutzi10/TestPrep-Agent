package com.example.testprep_agent.data;

import java.util.Date;

public class Agent {
    private String name;
    private int payment;
    private Date employmentDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public Date getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    public Agent() {
    }

    public Agent(String name, int payment, Date employmentDate) {
        this.name = name;
        this.payment = payment;
        this.employmentDate = employmentDate;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "name='" + name + '\'' +
                ", payment=" + payment +
                ", employmentDate=" + employmentDate +
                '}';
    }
}
