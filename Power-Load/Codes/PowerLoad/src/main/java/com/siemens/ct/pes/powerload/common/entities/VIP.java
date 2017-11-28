package com.siemens.ct.pes.powerload.common.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the M_VIP database table.
 * 
 */
@Entity
@Table(name="M_VIP")
@NamedQuery(name="VIP.findAll", query="SELECT v FROM VIP v")
public class VIP implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String address;

	private BigDecimal capacity;

	@Column(name="dept_id")
	private int deptId;

	@Column(name="industry_new")
	private int industryNew;

	@Column(name="industry_old")
	private int industryOld;

	private String name;

	private int status;

	private int suspicion;

	@Column(name="vlevel_id")
	private int vlevelId;

	public VIP() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public int getIndustryNew() {
		return this.industryNew;
	}

	public void setIndustryNew(int industryNew) {
		this.industryNew = industryNew;
	}

	public int getIndustryOld() {
		return this.industryOld;
	}

	public void setIndustryOld(int industryOld) {
		this.industryOld = industryOld;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSuspicion() {
		return this.suspicion;
	}

	public void setSuspicion(int suspicion) {
		this.suspicion = suspicion;
	}

	public int getVlevelId() {
		return this.vlevelId;
	}

	public void setVlevelId(int vlevelId) {
		this.vlevelId = vlevelId;
	}

}