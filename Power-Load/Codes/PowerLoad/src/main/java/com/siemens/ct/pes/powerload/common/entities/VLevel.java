package com.siemens.ct.pes.powerload.common.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the md_vlevel database table.
 * 
 */
@Entity
@Table(name = "MD_VLEVEL")
@NamedQuery(name = "VLevel.findAll", query = "SELECT v FROM VLevel v")
public class VLevel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int id;

    private String descrb;

    public VLevel() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescrb() {
        return this.descrb;
    }

    public void setDescrb(String descrb) {
        this.descrb = descrb;
    }

}