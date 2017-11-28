package com.siemens.ct.pes.powerload.trans.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the A_TRANS_INDUSTRY_PARAMETER database table.
 * 
 */
@Entity
@Table(name = "A_TRANS_INDUSTRY_PARAMETER")
@NamedQuery(name = "TransIndustryParameter.findAll", query = "SELECT t FROM TransIndustryParameter t")
public class TransIndustryParameter implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int id;

    @Column(name = "matrix_id")
    private int matrixId;

    private BigDecimal param;

    @Column(name = "trans_id")
    private int transId;

    public TransIndustryParameter() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMatrixId() {
        return this.matrixId;
    }

    public void setMatrixId(int matrixId) {
        this.matrixId = matrixId;
    }

    public BigDecimal getParam() {
        return this.param;
    }

    public void setParam(BigDecimal param) {
        this.param = param;
    }

    public int getTransId() {
        return this.transId;
    }

    public void setTransId(int transId) {
        this.transId = transId;
    }

}