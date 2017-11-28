package com.siemens.ct.pes.powerload.common.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the A_INDUSTRY database table.
 * 
 */
@Entity
@Table(name = "A_INDUSTRY")
@NamedQuery(name = "IndustryClustered.findAll", query = "SELECT i FROM IndustryClustered i")
public class IndustryClustered implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int id;

    private BigDecimal high;

    private BigDecimal low;

    private BigDecimal middle;

    private String name;

    public IndustryClustered() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getHigh() {
        return this.high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return this.low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getMiddle() {
        return this.middle;
    }

    public void setMiddle(BigDecimal middle) {
        this.middle = middle;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}