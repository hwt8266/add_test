package com.siemens.ct.pes.powerload.common.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the MD_INDUSTRY database table.
 * 
 */
@Entity
@Table(name="MD_INDUSTRY")
@NamedQuery(name="Industry.findAll", query="SELECT i FROM Industry i")
public class Industry implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private BigDecimal high;

	private BigDecimal low;

	private BigDecimal middle;

	private String name;

	public Industry() {
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