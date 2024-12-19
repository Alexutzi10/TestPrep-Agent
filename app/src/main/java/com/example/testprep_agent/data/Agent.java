package com.example.testprep_agent.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "agents")
public class Agent {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private int payment;
    private Date employmentDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    @Ignore
    public Agent() {
    }

    @Ignore
    public Agent(String name, int payment, Date employmentDate) {
        this.name = name;
        this.payment = payment;
        this.employmentDate = employmentDate;
    }

    public Agent(long id, String name, int payment, Date employmentDate) {
        this.id = id;
        this.name = name;
        this.payment = payment;
        this.employmentDate = employmentDate;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", payment=" + payment +
                ", employmentDate=" + employmentDate +
                '}';
    }
}
