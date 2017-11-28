package com.siemens.ct.pes.powerload.area.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the m_area_tran database table.
 * 
 */
@Embeddable
public class AreaTranPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="tran_id")
	private int tranId;

	@Column(name="area_id")
	private int areaId;

	public AreaTranPK() {
	}
	public int getTranId() {
		return this.tranId;
	}
	public void setTranId(int tranId) {
		this.tranId = tranId;
	}
	public int getAreaId() {
		return this.areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AreaTranPK)) {
			return false;
		}
		AreaTranPK castOther = (AreaTranPK)other;
		return 
			(this.tranId == castOther.tranId)
			&& (this.areaId == castOther.areaId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.tranId;
		hash = hash * prime + this.areaId;
		
		return hash;
	}
}