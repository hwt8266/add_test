package com.siemens.ct.pes.powerload.common.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the APP_DISPLAY_ITEM database table.
 * 
 */
@Entity
@Table(name = "APP_DISPLAY_ITEM")
@NamedQueries({ @NamedQuery(name = "DisItem.findAll", query = "SELECT d FROM DisItem d"),
        @NamedQuery(name = "DisItem.findByType", query = "SELECT d FROM DisItem d WHERE d.id.itemType = :type") })
public class DisItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private DisItemPK id;

    @Column(name = "item_name")
    private String itemName;

    public DisItem() {
    }

    public DisItemPK getId() {
        return this.id;
    }

    public void setId(DisItemPK id) {
        this.id = id;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}