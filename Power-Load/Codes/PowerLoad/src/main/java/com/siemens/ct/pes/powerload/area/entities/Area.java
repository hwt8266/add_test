package com.siemens.ct.pes.powerload.area.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the m_area database table.
 * 
 */
@Entity
@Table(name = "M_AREA")
@NamedQueries({ @NamedQuery(name = "Area.findAll", query = "SELECT a FROM Area a"),
        @NamedQuery(name = "Area.findAllByID", query = "SELECT a FROM Area a WHERE a.id = :id") })
public class Area implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int id;

    private BigDecimal increase;

    private String name;

    private String remark;

    public Area() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getIncrease() {
        return this.increase;
    }

    public void setIncrease(BigDecimal increase) {
        this.increase = increase;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}