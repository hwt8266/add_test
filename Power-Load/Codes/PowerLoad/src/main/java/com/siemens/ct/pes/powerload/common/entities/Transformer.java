package com.siemens.ct.pes.powerload.common.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the M_TRANS database table.
 * 
 */
@Entity
@Table(name = "M_TRANS")
@NamedQueries({
	@NamedQuery(name = "Transformer.findAll", query = "SELECT t FROM Transformer t ORDER BY t.transType DESC"),
	@NamedQuery(name = "Transformer.findByID", query = "SELECT t FROM Transformer t WHERE t.id = :id"),
	@NamedQuery(name = "Transformer.findByIDs", query = "SELECT t FROM Transformer t WHERE t.id IN :ids"),
	@NamedQuery(name = "Transformer.findByType", query = "SELECT t FROM Transformer t WHERE t.transType = :type") })
public class Transformer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int id;

    private BigDecimal capacity;

    @Column(name = "dept_id")
    private int deptId;

    private String descrb;

    @Column(name = "line_id")
    private int lineId;

    private String status;

    @Column(name = "trans_type")
    private int transType;

    private int vlevel;

    public Transformer() {
    }

    public int getId() {
	return this.id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public BigDecimal getCapacity() {
	return this.capacity;
    }

    public void setCapacity(BigDecimal capacity) {
	this.capacity = capacity;
    }

    public int getDeptId() {
	return this.deptId;
    }

    public void setDeptId(int deptId) {
	this.deptId = deptId;
    }

    public String getDescrb() {
	return this.descrb;
    }

    public void setDescrb(String descrb) {
	this.descrb = descrb;
    }

    public int getLineId() {
	return this.lineId;
    }

    public void setLineId(int lineId) {
	this.lineId = lineId;
    }

    public String getStatus() {
	return this.status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public int getTransType() {
	return this.transType;
    }

    public void setTransType(int transType) {
	this.transType = transType;
    }

    public int getVlevel() {
	return this.vlevel;
    }

    public void setVlevel(int vlevel) {
	this.vlevel = vlevel;
    }

}