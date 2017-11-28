package com.siemens.ct.pes.powerload.common.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the md_tran_type database table.
 * 
 */
@Entity
@Table(name = "MD_TRAN_TYPE")
@NamedQuery(name = "TranType.findAll", query = "SELECT t FROM TranType t")
public class TranType implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int type;

    private String name;

    public TranType() {
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}