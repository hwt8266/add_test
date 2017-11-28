package com.siemens.ct.pes.powerload.common.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the M_MEASE database table.
 * 
 */
@Entity
@Table(name="M_MEASE")
@NamedQuery(name="Mease.findAll", query="SELECT m FROM Mease m")
public class Mease implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String descrb;

	@Column(name="line_id")
	private int lineId;

	@Column(name="subarea_id")
	private int subareaId;

	@Column(name="trans_id")
	private int transId;

	@Column(name="vip_id")
	private int vipId;

	public Mease() {
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

	public int getLineId() {
		return this.lineId;
	}

	public void setLineId(int lineId) {
		this.lineId = lineId;
	}

	public int getSubareaId() {
		return this.subareaId;
	}

	public void setSubareaId(int subareaId) {
		this.subareaId = subareaId;
	}

	public int getTransId() {
		return this.transId;
	}

	public void setTransId(int transId) {
		this.transId = transId;
	}

	public int getVipId() {
		return this.vipId;
	}

	public void setVipId(int vipId) {
		this.vipId = vipId;
	}

}