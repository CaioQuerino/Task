/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.r2m.praticar.taskapplication.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 *
 * @author caioq
 */

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    
    
    private String zipCode;
    private String neighborhood;
    private String road;
    private String state;
    private String city;
    private String complement;
    private Integer number;

    public Address() {}
    
    public Address(String zipCode, String neighborhood, String road, 
            String state, String city, String complement, Integer number) 
    {
        this.zipCode = zipCode;
        this.neighborhood = neighborhood;
        this.state = state;
        this.city = city;
        this.complement = complement;
        this.number = number;
        this.road = road;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
    
    public void setNumber(String number) {
        try {
            this.number = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            this.number = null;
        }
    }  
    
    public String getRoad() {
        return road;
    }
    
    public void setRoad(String road) {
        this.road = road;
    }
    
    @Override
    public String toString() {
        return "Address{" +
                "zipCode='" + zipCode + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", road='" + road + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", complement='" + complement + '\'' +
                ", number=" + number +
                '}';
    }
}