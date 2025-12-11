package com.java.spr.agentservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "agent")
public class Agent {
    @Id
    private String agentId;
    private String firstName;
    private String lastName;
    private String city;
    private String state;
    private double premiumPaid;

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getPremiumPaid() {
        return premiumPaid;
    }

    public void setPremiumPaid(double premiumPaid) {
        this.premiumPaid = premiumPaid;
    }
}
