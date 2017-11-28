package com.siemens.ct.pes.powerload.area.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the m_area_tran database table.
 * 
 */
@Entity
@Table(name = "M_AREA_TRAN")
@NamedQueries({ @NamedQuery(name = "AreaTran.findAll", query = "SELECT a FROM AreaTran a"),
	@NamedQuery(name = "AreaTran.findByTranID", query = "SELECT a FROM AreaTran a WHERE a.id.tranId = :tranId"),
	@NamedQuery(name = "AreaTran.findByTranIDs", query = "SELECT a FROM AreaTran a WHERE a.id.tranId in :tranIds"),
	@NamedQuery(name = "AreaTran.findByIDs", query = "SELECT a FROM AreaTran a WHERE a.id.areaId = :areaId AND a.id.tranId = :tranId") })
public class AreaTran implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private AreaTranPK id;

    @Column(name = "high_min")
    private int highMin;

    private BigDecimal increase;

    @Column(name = "low_min")
    private int lowMin;

    @Column(name = "over_min")
    private int overMin;

    public AreaTran() {
    }

    public AreaTranPK getId() {
	return this.id;
    }

    public void setId(AreaTranPK id) {
	this.id = id;
    }

    public int getHighMin() {
	return this.highMin;
    }

    public void setHighMin(int highMin) {
	this.highMin = highMin;
    }

    public BigDecimal getIncrease() {
	return this.increase;
    }

    public void setIncrease(BigDecimal increase) {
	this.increase = increase;
    }

    public int getLowMin() {
	return this.lowMin;
    }

    public void setLowMin(int lowMin) {
	this.lowMin = lowMin;
    }

    public int getOverMin() {
	return this.overMin;
    }

    public void setOverMin(int overMin) {
	this.overMin = overMin;
    }

}