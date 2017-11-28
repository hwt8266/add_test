package com.siemens.ct.pes.powerload.common.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the M_DEPT database table.
 * 
 */
@Entity
@Table(name = "M_DEPT")
@NamedQueries({ @NamedQuery(name = "Dept.findAll", query = "SELECT t FROM Dept t"),
        @NamedQuery(name = "Dept.findByIDs", query = "SELECT t FROM Dept t WHERE t.id IN :ids"),
        @NamedQuery(name = "Dept.findByPIDs", query = "SELECT t FROM Dept t WHERE t.parentId IN :pids"),
        @NamedQuery(name = "Dept.findByType", query = "SELECT t FROM Dept t WHERE t.type = :Type") })
public class Dept implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int id;

    private String name;

    @Column(name = "parent_id")
    private int parentId;

    private int type;

    public Dept() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return this.parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

}