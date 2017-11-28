package com.siemens.ct.pes.powerload.common.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the APP_DISPLAY_ITEM database table.
 * 
 */
@Embeddable
public class DisItemPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="item_id")
	private int itemId;

	@Column(name="item_type")
	private int itemType;

	public DisItemPK() {
	}
	public int getItemId() {
		return this.itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getItemType() {
		return this.itemType;
	}
	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DisItemPK)) {
			return false;
		}
		DisItemPK castOther = (DisItemPK)other;
		return 
			(this.itemId == castOther.itemId)
			&& (this.itemType == castOther.itemType);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.itemId;
		hash = hash * prime + this.itemType;
		
		return hash;
	}
}