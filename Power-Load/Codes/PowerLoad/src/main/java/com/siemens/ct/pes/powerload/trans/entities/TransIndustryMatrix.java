package com.siemens.ct.pes.powerload.trans.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the A_TRANS_INDUSTRY_MATRIX database table.
 * 
 */
@Entity
@Table(name = "A_TRANS_INDUSTRY_MATRIX")
@NamedQueries({ @NamedQuery(name = "TransIndustryMatrix.findAll", query = "SELECT t FROM TransIndustryMatrix t"),
        @NamedQuery(name = "TransIndustryMatrix.findByIndustry",
                query = "SELECT t FROM TransIndustryMatrix t WHERE t.industryId = :industryId") })
public class TransIndustryMatrix implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int id;

    @Column(name = "dev_level")
    private int devLevel;

    @Column(name = "industry_id")
    private int industryId;

    public TransIndustryMatrix() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDevLevel() {
        return this.devLevel;
    }

    public void setDevLevel(int devLevel) {
        this.devLevel = devLevel;
    }

    public int getIndustryId() {
        return this.industryId;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }

}